package info.unterrainer.commons.httpserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import info.unterrainer.commons.httpserver.accessmanager.HttpAccessManager;
import info.unterrainer.commons.httpserver.accessmanager.UserAccessInterceptor;
import info.unterrainer.commons.httpserver.daos.CoreDaoProvider;
import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.enums.ResponseType;
import info.unterrainer.commons.httpserver.exceptions.HttpException;
import info.unterrainer.commons.httpserver.exceptions.NotFoundException;
import info.unterrainer.commons.httpserver.exceptions.UnauthorizedException;
import info.unterrainer.commons.httpserver.handlers.AppNameHandler;
import info.unterrainer.commons.httpserver.handlers.AppVersionHandler;
import info.unterrainer.commons.httpserver.handlers.DateTimeHandler;
import info.unterrainer.commons.httpserver.handlers.HealthHandler;
import info.unterrainer.commons.httpserver.handlers.PostmanCollectionHandler;
import info.unterrainer.commons.httpserver.jsons.MessageJson;
import info.unterrainer.commons.jreutils.ShutdownHook;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsonmapper.JsonMapper;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import info.unterrainer.commons.serialization.objectmapper.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;

@Slf4j
public class HttpServer {

	private static final String VERSION_FQN = "info.unterrainer.commons.httpserver.Information";
	private Javalin javalin;
	private HttpServerConfiguration config;
	private String applicationName;
	private JsonMapper jsonMapper;
	private ObjectMapper objectMapper;
	private final List<HandlerGroup> handlerGroups = new ArrayList<>();
	private List<HandlerInstance> handlerInstances = new ArrayList<>();
	/**
	 * Routes (keyed as "{HTTP-METHOD} {normalized-path-template}") that opted out of request-body
	 * buffering via {@link #postRaw}/{@link #putRaw}. For these the {@code unzip} before-handler never
	 * reads the body, leaving the raw {@code InputStream} untouched for the handler to stream.
	 */
	private final Set<String> rawBodyRoutes = new HashSet<>();
	ExecutorService executorService;
	Consumer<Javalin> beforeStartHandler;
	List<String> appVersionFqns;
	@Getter
	@Setter
	private UserAccessInterceptor userAccessInterceptor;
	@Getter
	@Setter
	private List<String> enumLookupFqnForInterceptorParser;

	private HttpServer() {
	}

	@Builder
	private HttpServer(final String configPrefix, final String applicationName, final ObjectMapper objectMapper,
			final JsonMapper jsonMapper, final ExecutorService executorService,
			final Consumer<Javalin> beforeStartHandler, final String... appVersionFqns) {
		config = HttpServerConfiguration.read(configPrefix);
		this.beforeStartHandler = beforeStartHandler;
		this.applicationName = applicationName;
		this.executorService = executorService;
		this.appVersionFqns = new ArrayList<>(List.of(Optional.ofNullable(appVersionFqns).orElse(new String[0])));
		if (!this.appVersionFqns.contains(VERSION_FQN))
			this.appVersionFqns.add(VERSION_FQN);
		if (executorService == null) {
			this.executorService = new ThreadPoolExecutor(200, 200, 60L, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>());
			((ThreadPoolExecutor) this.executorService).allowCoreThreadTimeOut(true);
		}
		if (applicationName == null)
			throw new IllegalArgumentException("The application-name cannot be null");
		this.jsonMapper = jsonMapper;
		if (this.jsonMapper == null)
			this.jsonMapper = JsonMapper.create();
		this.objectMapper = objectMapper;
		if (this.objectMapper == null)
			this.objectMapper = new ObjectMapper();

		create();
	}

	public void addHandlerGroup(final HandlerGroup group) {
		handlerGroups.add(group);
	}

	public <P extends BasicJpa, J extends BasicJson, E> GenericHandlerGroupBuilder<P, J, E> handlerGroupFor(
			final Class<P> jpaType, final Class<J> jsonType, final CoreDaoProvider<P, E> coreDaoProvider) {
		return new GenericHandlerGroupBuilder<>(this, jpaType, jsonType, coreDaoProvider.getCoreDao());
	}

	private void create() {
		javalin = Javalin.create(c -> {
			c.accessManager(new HttpAccessManager(config.keycloakHost(), config.keycloakRealm()));
			c.enableCorsForAllOrigins();
		});

		if (beforeStartHandler != null) {
			beforeStartHandler.accept(javalin);
		}
		javalin.start(config.host(), config.port());

		javalin.before(ctx -> ctx.attribute(Attribute.JAVALIN_SERVER, this));
		javalin.before(ctx -> ctx.attribute(Attribute.RESPONSE_TYPE, ResponseType.JSON));
		javalin.before(ctx -> ctx.attribute(Attribute.RESPONSE_CONTENT_TYPE, "application/json"));
		javalin.before(this::unzip);

		javalin.after(this::render);

		javalin.error(404, ctx -> {
			ctx.result(jsonMapper.toStringFrom(MessageJson.builder()
					.message(NotFoundException.HTTP_STATUS + " " + NotFoundException.HTTP_TEXT)
					.build())).contentType("application/json").status(NotFoundException.HTTP_STATUS);
		}).exception(Exception.class, (e, ctx) -> {
			handleException(e, ctx);
		});

		get("/", new AppNameHandler(applicationName));
		get("/version", new AppVersionHandler(appVersionFqns));
		get("/datetime", new DateTimeHandler());
		get("/health", new HealthHandler());
		get("/postman", new PostmanCollectionHandler());

		registerShutdownHook();
	}

	private void registerShutdownHook() {
		ShutdownHook.register(() -> {
			executorService.shutdown();
			try {
				if (!executorService.awaitTermination(1, TimeUnit.SECONDS))
					executorService.shutdownNow();
			} catch (InterruptedException e) {
				executorService.shutdownNow();
			}
		});
	}

	private void handleException(final Exception e, final Context ctx) {
		int status;
		String message;
		if (HttpException.class.isAssignableFrom(e.getClass())) {
			HttpException h = (HttpException) e;
			status = h.getHttpStatus();
			message = status + " " + h.getHttpText();
		} else {
			status = 500;
			message = "500 Internal Server Error";
			if (e instanceof UnauthorizedException)
				log.debug(e.getMessage());
			else
				log.error(e.getMessage(), e);
		}
		ctx.result(jsonMapper.toStringFrom(MessageJson.builder().message(message).build()))
				.contentType("application/json")
				.status(status);
	}

	public void start() {
		for (HandlerGroup g : handlerGroups)
			g.addHandlers(this);

		for (HandlerInstance hi : handlerInstances)
			javalin.addHandler(hi.handlerType(), hi.path(), hi.handler(), hi.roles());
	}

	public HttpServer get(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder()
				.path(path)
				.handlerType(HandlerType.GET)
				.handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles)))
				.build());
		return this;
	}

	public HttpServer put(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder()
				.path(path)
				.handlerType(HandlerType.PUT)
				.handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles)))
				.build());
		return this;
	}

	public HttpServer patch(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder()
				.path(path)
				.handlerType(HandlerType.PATCH)
				.handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles)))
				.build());
		return this;
	}

	public HttpServer post(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder()
				.path(path)
				.handlerType(HandlerType.POST)
				.handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles)))
				.build());
		return this;
	}

	public HttpServer delete(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder()
				.path(path)
				.handlerType(HandlerType.DELETE)
				.handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles)))
				.build());
		return this;
	}

	/**
	 * Registers a POST route that opts out of request-body buffering.
	 * <p>
	 * Unlike {@link #post}, the {@code unzip} before-handler will not call {@code ctx.body()} for this
	 * route, so {@link Attribute#REQUEST_BODY} stays {@code null} and the raw, unbuffered request body
	 * remains available. The handler must read the body via {@code ctx.req.getInputStream()} (and must
	 * NOT call {@code ctx.body()}/{@code ctx.bodyAsBytes()}/{@code ctx.bodyAsInputStream()}, all of which
	 * buffer it). Use this for
	 * streaming large uploads straight to their destination. Auth/roles are enforced exactly as with
	 * {@link #post}.
	 */
	public HttpServer postRaw(final String path, final Handler handler, final Role... roles) {
		rawBodyRoutes.add(rawRouteKey(HandlerType.POST, path));
		return post(path, handler, roles);
	}

	/**
	 * Registers a PUT route that opts out of request-body buffering. See {@link #postRaw} for the
	 * contract the handler must follow.
	 */
	public HttpServer putRaw(final String path, final Handler handler, final Role... roles) {
		rawBodyRoutes.add(rawRouteKey(HandlerType.PUT, path));
		return put(path, handler, roles);
	}

	private static String rawRouteKey(final HandlerType method, final String path) {
		return method.name() + " " + normalizePath(path);
	}

	private boolean isRawBodyRoute(final String method, final String requestPath) {
		if (rawBodyRoutes.isEmpty())
			return false;
		String normalizedRequest = normalizePath(requestPath);
		for (String key : rawBodyRoutes) {
			int separator = key.indexOf(' ');
			if (!key.substring(0, separator).equalsIgnoreCase(method))
				continue;
			if (pathMatches(key.substring(separator + 1), normalizedRequest))
				return true;
		}
		return false;
	}

	/**
	 * Strips a leading/trailing slash and surrounding whitespace so that registered path templates and
	 * incoming request paths compare regardless of slash style ("/foo/" vs "foo").
	 */
	static String normalizePath(final String path) {
		if (path == null)
			return "";
		String p = path.trim();
		while (p.startsWith("/"))
			p = p.substring(1);
		while (p.endsWith("/"))
			p = p.substring(0, p.length() - 1);
		return p;
	}

	/**
	 * Matches a Javalin path template (already normalized) against a normalized request path, treating
	 * path-parameter segments ({@code {id}}, {@code <id>}) and wildcards ({@code *}) as matching any
	 * single segment.
	 */
	static boolean pathMatches(final String template, final String requestPath) {
		String[] t = template.isEmpty() ? new String[0] : template.split("/");
		String[] r = requestPath.isEmpty() ? new String[0] : requestPath.split("/");
		if (t.length != r.length)
			return false;
		for (int i = 0; i < t.length; i++) {
			String segment = t[i];
			boolean isParam = segment.equals("*") || segment.startsWith("{") || segment.startsWith("<");
			if (isParam)
				continue;
			if (!segment.equals(r[i]))
				return false;
		}
		return true;
	}

	private void unzip(final Context ctx) throws IOException {
		// Raw-body routes (postRaw/putRaw) must keep their request InputStream untouched: never read it
		// here, so REQUEST_BODY stays null and the handler can stream the raw body.
		if (isRawBodyRoute(ctx.method(), ctx.path()))
			return;

		String body = ctx.body();

		if (body == null)
			return;

		if ("gzip".equalsIgnoreCase(ctx.header("Content-Encoding"))) {
			BufferedSource bs = Okio.buffer(Okio.source(ctx.bodyAsInputStream()));
			GzipSource gzipSource = new GzipSource(bs);
			body = Okio.buffer(gzipSource).readUtf8();
		}

		ctx.bodyAsInputStream().reset();
		ctx.attribute(Attribute.REQUEST_BODY, body);
	}

	private void render(final Context ctx) throws IOException {

		Object dto = ctx.attribute(Attribute.RESPONSE_OBJECT);
		if (dto != null)
			switch ((ResponseType) ctx.attribute(Attribute.RESPONSE_TYPE)) {
			case JSON:
				ctx.result(jsonMapper.toStringFrom(dto));
				break;
			default:
				ctx.result((String) dto);
			}

		Integer status = ctx.attribute(Attribute.RESPONSE_STATUS);
		if (status != null)
			ctx.status(status);

		String contentType = ctx.attribute(Attribute.RESPONSE_CONTENT_TYPE);
		if (contentType != null)
			ctx.contentType(contentType);
	}
}
