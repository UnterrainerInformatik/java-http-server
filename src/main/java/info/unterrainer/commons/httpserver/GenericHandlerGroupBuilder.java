package info.unterrainer.commons.httpserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;

import info.unterrainer.commons.httpserver.daos.CoreDao;
import info.unterrainer.commons.httpserver.daos.ParamMap;
import info.unterrainer.commons.httpserver.enums.Endpoint;
import info.unterrainer.commons.httpserver.extensions.AsyncExtensionContextMapper;
import info.unterrainer.commons.httpserver.interceptors.InterceptorData;
import info.unterrainer.commons.httpserver.interceptors.InterceptorParamBuilder;
import info.unterrainer.commons.httpserver.interceptors.delegates.GetListInterceptor;
import info.unterrainer.commons.httpserver.rql.RqlData;
import info.unterrainer.commons.httpserver.rql.RqlUtils;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.JsonMapper;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.core.security.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GenericHandlerGroupBuilder<P extends BasicJpa, J extends BasicJson, E> {

	private final HttpServer server;
	private final Class<P> jpaType;
	private final Class<J> jsonType;
	private final CoreDao<P, E> dao;
	private JsonMapper jsonMapper;
	private MapperFactory orikaFactory;
	private String path;
	private List<Endpoint> endpoints = new ArrayList<>();
	private List<GetListInterceptor> getListInterceptors = new ArrayList<>();
	private List<AsyncExtensionContextMapper> asyncExtensionContextMappers = new ArrayList<>();
	private ExecutorService executorService;

	HandlerExtensions<P, J, E> extensions = new HandlerExtensions<>();
	private LinkedHashMap<Endpoint, Role[]> accessRoles = new LinkedHashMap<>();

	public HttpServer add() {
		if (jsonMapper == null)
			jsonMapper = JsonMapper.create();
		if (orikaFactory == null)
			orikaFactory = new DefaultMapperFactory.Builder().build();
		if (executorService == null)
			executorService = server.executorService;
		GenericHandlerGroup<P, J, E> handlerGroupInstance = new GenericHandlerGroup<>(dao, jpaType, jsonType,
				jsonMapper, orikaFactory.getMapperFacade(), path, endpoints, getListInterceptors, extensions,
				asyncExtensionContextMappers, accessRoles, executorService);
		server.addHandlerGroup(handlerGroupInstance);
		return server;
	}

	public AddonBuilder<P, J, E> extension() {
		return new AddonBuilder<>(this);
	}

	public GenericHandlerGroupBuilder<P, J, E> addRoleFor(final Endpoint endpoint, final Role... roles) {
		accessRoles.put(endpoint, roles);
		return this;
	}

	public GenericHandlerGroupBuilder<P, J, E> jsonMapper(final JsonMapper jsonMapper) {
		this.jsonMapper = jsonMapper;
		return this;
	}

	public GenericHandlerGroupBuilder<P, J, E> orikaFactory(final MapperFactory orikaFactory) {
		this.orikaFactory = orikaFactory;
		return this;
	}

	public GenericHandlerGroupBuilder<P, J, E> path(final String path) {
		this.path = path;
		return this;
	}

	public GenericHandlerGroupBuilder<P, J, E> endpoints(final ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}

	public GenericHandlerGroupBuilder<P, J, E> endpoints(final Endpoint... endpoints) {
		this.endpoints.addAll(Arrays.asList(endpoints));
		return this;
	}

	public GenericHandlerGroupBuilder<P, J, E> getListInterceptor(final GetListInterceptor interceptor) {
		getListInterceptors.add(interceptor);
		return this;
	}

	/**
	 * Here you may register {@link AsyncExtensionContextMapper}s that allow you to
	 * map attributes located in the Javalin-context to an offline, asynchronous one
	 * which you can address within asynchronous extension methods.
	 *
	 * @param asyncExtensionContextMapper a mapper of type
	 *                                    {@link AsyncExtensionContextMapper} you
	 *                                    may register as an extension that moves
	 *                                    variables from the Javalin-context to the
	 *                                    async-context.
	 * @return an instance of this object to assure a fluent interface
	 */
	public GenericHandlerGroupBuilder<P, J, E> asyncExtensionContextMapper(
			final AsyncExtensionContextMapper asyncExtensionContextMapper) {
		asyncExtensionContextMappers.add(asyncExtensionContextMapper);
		return this;
	}

	/**
	 * Gives you a {@link InterceptorParamBuilder} object that allows you to declare
	 * an interceptor with minimal code.<br>
	 * You can specify every important part of it (select-clause, where-clause,
	 * join-clause and order-by-clause) and utilizes its own parser for the
	 * where-clause ({@link InterceptorParamBuilder#query(String)}).
	 *
	 * @return a new instance of the {@link InterceptorParamBuilder}
	 */
	public InterceptorParamBuilder<P, J, E> getListInterceptor() {
		return new InterceptorParamBuilder<>(this, (passedData, query) -> {
			getListInterceptors.add((ctx, hu) -> {
				RqlUtils rqlUtils = new RqlUtils(ctx, hu, server.getEnumLookupFqnForInterceptorParser());
				RqlData data = rqlUtils.parseRql(query);
				InterceptorData r = InterceptorData.builder()
						.selectClause(passedData.getSelectClause())
						.whereClause(data.getParsedCommandAsString())
						.joinClause(passedData.getJoinClause())
						.params(ParamMap.builder().parameters(data.getParams()).build())
						.partOfQueryString(data.getQueryStringAsString())
						.build();
				return r;
			});
		});
	}
}
