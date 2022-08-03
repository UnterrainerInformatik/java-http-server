package info.unterrainer.commons.httpserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import info.unterrainer.commons.httpserver.daos.CoreDao;
import info.unterrainer.commons.httpserver.daos.DaoTransaction;
import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.enums.Endpoint;
import info.unterrainer.commons.httpserver.enums.QueryField;
import info.unterrainer.commons.httpserver.exceptions.BadRequestException;
import info.unterrainer.commons.httpserver.extensions.AsyncExtensionContext;
import info.unterrainer.commons.httpserver.extensions.AsyncExtensionContextBaseMapper;
import info.unterrainer.commons.httpserver.extensions.AsyncExtensionContextMapper;
import info.unterrainer.commons.httpserver.interceptors.InterceptorData;
import info.unterrainer.commons.httpserver.interceptors.delegates.GetListInterceptor;
import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsonmapper.JsonMapper;
import info.unterrainer.commons.serialization.jsonmapper.exceptions.JsonMappingException;
import info.unterrainer.commons.serialization.jsonmapper.exceptions.JsonProcessingException;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import info.unterrainer.commons.serialization.objectmapper.ObjectMapper;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GenericHandlerGroup<P extends BasicJpa, J extends BasicJson, E> implements HandlerGroup {

	private final CoreDao<P, E> dao;
	private final Class<P> jpaType;
	private final Class<J> jsonType;
	private final JsonMapper jsonMapper;
	private final ObjectMapper objectMapper;
	private final String path;
	private final List<Endpoint> endpoints;
	private final List<GetListInterceptor> getListInterceptors;
	private final HandlerExtensions<P, J, E> extensions;
	private final List<AsyncExtensionContextMapper> asyncExtensionContextMappers;
	private final LinkedHashMap<Endpoint, Role[]> accessRoles;
	private final ExecutorService executorService;
	private final HandlerUtils hu = new HandlerUtils();

	@Override
	public void addHandlers(final HttpServer server) {
		String p = path;
		if (!p.startsWith("/"))
			p = "/" + p;
		String pId = p + "/:" + QueryField.ID;

		List<Endpoint> endpointList;
		endpointList = List.of(Endpoint.ALL, Endpoint.READONLY, Endpoint.GET_SINGLE);
		if (endpointsToCreate(endpointList))
			server.get(pId, this::getEntry, rolesFor(endpointList));

		endpointList = List.of(Endpoint.ALL, Endpoint.READONLY, Endpoint.GET_LIST);
		if (endpointsToCreate(endpointList))
			server.get(p, this::getList, rolesFor(endpointList));

		endpointList = List.of(Endpoint.ALL, Endpoint.WRITEONLY, Endpoint.CREATE);
		if (endpointsToCreate(endpointList))
			server.post(p, this::create, rolesFor(endpointList));

		endpointList = List.of(Endpoint.ALL, Endpoint.WRITEONLY, Endpoint.UPDATE_FULL);
		if (endpointsToCreate(endpointList))
			server.put(pId, this::fullUpdate, rolesFor(endpointList));

		endpointList = List.of(Endpoint.ALL, Endpoint.WRITEONLY, Endpoint.DELETE);
		if (endpointsToCreate(endpointList))
			server.delete(pId, this::delete, rolesFor(endpointList));
	}

	private boolean endpointsToCreate(final List<Endpoint> endpointList) {
		for (Endpoint endpoint : endpointList)
			if (endpoints.contains(endpoint))
				return true;
		return false;
	}

	private AsyncExtensionContext makeAsyncExtensionContextFor(final Context ctx) {
		AsyncExtensionContext asyncCtx = new AsyncExtensionContext();
		for (AsyncExtensionContextMapper e : asyncExtensionContextMappers)
			e.map(ctx, asyncCtx);
		AsyncExtensionContextBaseMapper m = new AsyncExtensionContextBaseMapper();
		m.map(ctx, asyncCtx);
		return asyncCtx;
	}

	private Role[] rolesFor(final List<Endpoint> endpointList) {
		List<Role> roles = new ArrayList<>();
		for (Entry<Endpoint, Role[]> entry : accessRoles.entrySet())
			if (endpointList.contains(entry.getKey()))
				roles.addAll(List.of(entry.getValue()));
		return roles.toArray(new Role[0]);
	}

	private void getEntry(final Context ctx) {
		DaoTransaction<E> transaction = dao.getTransactionManager().beginTransaction(ctx);

		P jpa = hu.getJpaById(ctx, transaction.getManager(), dao);
		J json = objectMapper.map(jsonType, jpa);
		json = extensions.runPostGetSingle(ctx, makeAsyncExtensionContextFor(ctx), transaction.getManager(),
				jpa.getId(), jpa, json, executorService);

		transaction.end();
		ctx.attribute(Attribute.RESPONSE_OBJECT, json);
	}

	private void getList(final Context ctx) {
		Long offset = hu.getQueryParamAsLong(ctx, QueryField.PAGINATION_OFFSET, 0L);
		Long size = hu.getQueryParamAsLong(ctx, QueryField.PAGINATION_SIZE, Long.MAX_VALUE);

		InterceptorData interceptorResult = InterceptorData.builder()
				.selectClause("o")
				.joinClause("")
				.whereClause("")
				.params(null)
				.orderByClause("")
				.partOfQueryString("")
				.build();
		for (GetListInterceptor interceptor : getListInterceptors)
			try {
				InterceptorData result = interceptor.intercept(ctx, hu);
				if (result != null) {
					interceptorResult = result;
					break;
				}
			} catch (Exception e) {
				log.debug("Interceptor threw an exception [{}]: [{}]. Ignoring.", e.getClass().getSimpleName(),
						e.getMessage());
			}

		DaoTransaction<E> transaction = dao.getTransactionManager().beginTransaction(ctx);

		ListJson<P> bList = dao.getList(transaction.getManager(), offset, size, interceptorResult.getSelectClause(),
				interceptorResult.getJoinClause(), interceptorResult.getWhereClause(), interceptorResult.getParams(),
				interceptorResult.getOrderByClause(), hu.getReadTenantIdsFrom(ctx));
		ListJson<J> jList = new ListJson<>();
		for (P entry : bList.getEntries())
			jList.getEntries().add(objectMapper.map(jsonType, entry));

		hu.setPaginationParamsFor(jList, offset, size, bList.getCount(), interceptorResult.getPartOfQueryString(), ctx);

		jList = extensions.runPostGetList(ctx, makeAsyncExtensionContextFor(ctx), transaction.getManager(), size,
				offset, bList, jList, executorService);

		transaction.end();
		ctx.attribute(Attribute.RESPONSE_OBJECT, jList);
	}

	private void create(final Context ctx) throws IOException {
		String b = ctx.attribute(Attribute.REQUEST_BODY);
		try {
			J json = jsonMapper.fromStringTo(jsonType, b);
			P mappedJpa = objectMapper.map(jpaType, json);
			DaoTransaction<E> transaction = dao.getTransactionManager().beginTransaction(ctx);

			mappedJpa = extensions.runPreInsert(ctx, makeAsyncExtensionContextFor(ctx), transaction.getManager(), json,
					mappedJpa, executorService);
			P createdJpa = dao.create(transaction.getManager(), mappedJpa, hu.getWriteTenantIdsFrom(ctx));
			J r = objectMapper.map(jsonType, createdJpa);

			r = extensions.runPostInsert(ctx, makeAsyncExtensionContextFor(ctx), transaction.getManager(), json,
					mappedJpa, createdJpa, r, executorService);

			transaction.end();
			ctx.attribute(Attribute.RESPONSE_OBJECT, r);

		} catch (JsonProcessingException | JsonMappingException e) {
			throw new BadRequestException();
		}
	}

	private void fullUpdate(final Context ctx) throws IOException {
		DaoTransaction<E> transaction = dao.getTransactionManager().beginTransaction(ctx);
		P jpa = hu.getJpaById(ctx, transaction.getManager(), dao);
		try {
			P detachedJpa = objectMapper.map(jpaType, jpaType, jpa);
			J json = jsonMapper.fromStringTo(jsonType, ctx.attribute(Attribute.REQUEST_BODY));
			P mappedJpa = objectMapper.map(jpaType, json);
			mappedJpa.setId(jpa.getId());
			mappedJpa.setCreatedOn(jpa.getCreatedOn());
			mappedJpa.setEditedOn(jpa.getEditedOn());

			mappedJpa = extensions.runPreModify(ctx, makeAsyncExtensionContextFor(ctx), transaction.getManager(),
					jpa.getId(), json, jpa, mappedJpa, executorService);

			P persistedJpa = dao.update(transaction.getManager(), mappedJpa, hu.getReadTenantIdsFrom(ctx));

			J r = objectMapper.map(jsonType, persistedJpa);
			r = extensions.runPostModify(ctx, makeAsyncExtensionContextFor(ctx), transaction.getManager(), jpa.getId(),
					json, detachedJpa, mappedJpa, persistedJpa, r, executorService);

			ctx.attribute(Attribute.RESPONSE_OBJECT, r);

		} catch (JsonProcessingException | JsonMappingException e) {
			throw new BadRequestException();
		} finally {
			transaction.end();
		}
	}

	private void delete(final Context ctx) {
		ctx.attribute(Attribute.RESPONSE_OBJECT, null);
		Long id = hu.checkAndGetId(ctx);
		DaoTransaction<E> transaction = dao.getTransactionManager().beginTransaction(ctx);

		Set<Long> tenants = hu.getReadTenantIdsFrom(ctx);
		P jpa = dao.getById(transaction.getManager(), id, tenants);
		id = extensions.runPreDelete(ctx, makeAsyncExtensionContextFor(ctx), transaction.getManager(), id, jpa,
				executorService);
		dao.delete(transaction.getManager(), id, hu.getReadTenantIdsFrom(ctx));
		ctx.status(204);
		extensions.runPostDelete(ctx, makeAsyncExtensionContextFor(ctx), transaction.getManager(), id, jpa,
				executorService);

		transaction.end();
	}
}
