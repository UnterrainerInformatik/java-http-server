package info.unterrainer.commons.httpserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;

import info.unterrainer.commons.httpserver.jsons.MessageJson;
import info.unterrainer.commons.serialization.JsonMapper;
import io.javalin.Javalin;
import io.javalin.core.security.Role;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import lombok.Builder;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class HttpServer {

	private HttpServerConfiguration config;
	private JsonMapper jsonMapper;
	private MapperFactory orikaFactory;
	private String applicationName;
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
	}

	public <P, J> GenericHandlerGroupBuilder<P, J> handlerGroupFor(final Class<P> jpaType, final Class<J> jsonType) {
		return new GenericHandlerGroupBuilder<>(this, jpaType, jsonType);
	}

	<P, J> void addGenericHandlerGroup(final GenericHandlerGroup<P, J> group) {

	}

	public void run() {
		Server server = new Server();
		ServerConnector connector = new ServerConnector(server);
		connector.setHost(config.host());
		connector.setPort(config.port());
		server.setConnectors(new ServerConnector[] { connector });

		Javalin app = Javalin.create(config -> {
			config.server(() -> server);
		}).start(config.port());

		get("/", ctx -> ctx.result(jsonMapper.toStringFrom(MessageJson.builder().message(applicationName).build())));
		get("/health", ctx -> ctx.result("healthy"));

		for (HandlerInstance hi : handlerInstances)
			app.addHandler(hi.handlerType(), hi.path(), hi.handler(), hi.roles());

		app.after(ctx -> {
			ctx.contentType("application/json");
		});

		app.exception(Exception.class, (e, ctx) -> {
			ctx.status(500);
			ctx.result(jsonMapper.toStringFrom(MessageJson.builder().message("500 Internal Server Error").build()));
		});
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
}
