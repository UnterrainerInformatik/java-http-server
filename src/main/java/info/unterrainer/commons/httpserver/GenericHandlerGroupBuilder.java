package info.unterrainer.commons.httpserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import info.unterrainer.commons.httpserver.daos.BasicDao;
import info.unterrainer.commons.httpserver.enums.Endpoint;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.JsonMapper;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GenericHandlerGroupBuilder<P extends BasicJpa, J extends BasicJson> {

	private final HttpServer server;
	private BasicDao<P> dao;
	private final Class<P> jpaType;
	private final Class<J> jsonType;
	private JsonMapper jsonMapper;
	private MapperFactory orikaFactory;
	private String path;
	private List<Endpoint> endpoints = new ArrayList<>();

	public HttpServer add() {
		if (jsonMapper == null)
			jsonMapper = JsonMapper.create();
		if (orikaFactory == null)
			orikaFactory = new DefaultMapperFactory.Builder().build();
		GenericHandlerGroup<P, J> handlerGroupInstance = new GenericHandlerGroup<>(dao, jpaType, jsonType,
				jsonMapper, orikaFactory.getMapperFacade(), path, endpoints);
		server.addHandlerGroup(handlerGroupInstance);
		return server;
	}

	public GenericHandlerGroupBuilder<P, J> dao(final BasicDao<P> dao) {
		this.dao = dao;
		return this;
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

	public GenericHandlerGroupBuilder<P, J> endpoints(final Endpoint... endpoints) {
		this.endpoints.addAll(Arrays.asList(endpoints));
		return this;
	}
}
