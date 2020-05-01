package info.unterrainer.commons.httpserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.exceptions.HttpException;
import info.unterrainer.commons.httpserver.handlers.AppNameHandler;
import info.unterrainer.commons.httpserver.handlers.DateTimeHandler;
import info.unterrainer.commons.httpserver.handlers.HealthHandler;
import info.unterrainer.commons.httpserver.jsons.MessageJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.JsonMapper;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.Javalin;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Slf4j
public class HttpServer {

	private Javalin javalin;
	private HttpServerConfiguration config;
	private String applicationName;
	private JsonMapper jsonMapper;
	private MapperFactory orikaFactory;
	private final List<HandlerGroup> handlerGroups = new ArrayList<>();
	private List<HandlerInstance> handlerInstances = new ArrayList<>();

	private HttpServer() {
	}

	@Builder
	private HttpServer(final String configPrefix, final String applicationName, final MapperFactory orikaFactory,
			final JsonMapper jsonMapper) {
		config = HttpServerConfiguration.read(configPrefix);
		this.applicationName = applicationName;
		if (applicationName == null)
			throw new IllegalArgumentException("The application-name cannot be null");
		this.jsonMapper = jsonMapper;
		if (this.jsonMapper == null)
			this.jsonMapper = JsonMapper.create();
		this.orikaFactory = orikaFactory;
		if (this.orikaFactory == null)
			this.orikaFactory = new DefaultMapperFactory.Builder().build();

		create();
	}

	public void addHandlerGroup(final HandlerGroup group) {
		handlerGroups.add(group);
	}

	public <P extends BasicJpa, J extends BasicJson> GenericHandlerGroupBuilder<P, J> handlerGroupFor(
			final Class<P> jpaType, final Class<J> jsonType) {
		return new GenericHandlerGroupBuilder<>(this, jpaType, jsonType);
	}

	private void create() {

		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setHost(config.host());
		connector.setPort(config.port());
		server.setConnectors(new ServerConnector[] { connector });

		javalin = Javalin.create(config -> {
			config.server(() -> server).enableCorsForAllOrigins();
		}).start(config.port());

		javalin.before(ctx -> ctx.attribute(Attribute.JAVALIN_SERVER, this));
		javalin.before(ctx -> ctx.contentType("application/json"));

		javalin.after(ctx -> render(ctx));
		javalin.after(ctx -> {
			ctx.contentType("application/json");
		});

		javalin.exception(Exception.class, (e, ctx) -> {
			int status;
			String message;
			if (e.getClass().isAssignableFrom(HttpException.class)) {
				HttpException h = (HttpException) e;
				status = h.getHttpStatus();
				message = status + " " + h.getHttpText();
			} else {
				status = 500;
				message = "500 Internal Server Error";
				log.error(e.getMessage(), e);
			}
			ctx.result(jsonMapper.toStringFrom(MessageJson.builder().message(message).build()))
					.contentType("application/json").status(status);
		});

		get("/", new AppNameHandler(applicationName));
		get("/datetime", new DateTimeHandler());
		get("/health", new HealthHandler());
	}

	public void start() {
		for (HandlerGroup g : handlerGroups)
			g.addHandlers(this);

		for (HandlerInstance hi : handlerInstances)
			javalin.addHandler(hi.handlerType(), hi.path(), hi.handler(), hi.roles());
	}

	public HttpServer get(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder().path(path).handlerType(HandlerType.GET).handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles))).build());
		return this;
	}

	public HttpServer put(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder().path(path).handlerType(HandlerType.PUT).handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles))).build());
		return this;
	}

	public HttpServer patch(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder().path(path).handlerType(HandlerType.PATCH).handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles))).build());
		return this;
	}

	public HttpServer post(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder().path(path).handlerType(HandlerType.POST).handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles))).build());
		return this;
	}

	public HttpServer delete(final String path, final Handler handler, final Role... roles) {
		handlerInstances.add(HandlerInstance.builder().path(path).handlerType(HandlerType.DELETE).handler(handler)
				.roles(new HashSet<>(Arrays.asList(roles))).build());
		return this;
	}

	private void render(final Context ctx) throws IOException {

		Object dto = ctx.attribute(Attribute.RESPONSE_OBJECT);
		if (dto != null)
			ctx.result(jsonMapper.toStringFrom(dto));

		Integer status = ctx.attribute(Attribute.RESPONSE_STATUS);
		if (status != null)
			ctx.status(status);
	}
}
