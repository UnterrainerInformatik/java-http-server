package info.unterrainer.commons.httpserver;

import info.unterrainer.commons.serialization.JsonMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GenericHandlerGroupBuilder<P, J> {

	private final HttpServer server;
	private final Class<P> jpaType;
	private final Class<J> jsonType;
	private JsonMapper jsonMapper;
	private MapperFactory orikaFactory;
	private String path;

	public HttpServer add() {
		GenericHandlerGroup<P, J> handlerGroupInstance = new GenericHandlerGroup<>(jpaType, jsonType, jsonMapper,
				orikaFactory, path);
		server.addGenericHandlerGroup(handlerGroupInstance);
		return server;
	}

	public GenericHandlerGroupBuilder<P, J> jsonMapper(final JsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
		return this;
	}

	public GenericHandlerGroupBuilder<P, J> orikaFactory(final MapperFactory orikaFactory) {
		this.orikaFactory = orikaFactory;
		return this;
	}

	public GenericHandlerGroupBuilder<P, J> path(final String path) {
		this.path = path;
		return this;
	}
}
