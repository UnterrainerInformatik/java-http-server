package info.unterrainer.commons.httpserver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

import info.unterrainer.commons.httpserver.daos.BasicDao;
import info.unterrainer.commons.httpserver.daos.DaoTransaction;
import info.unterrainer.commons.httpserver.daos.DaoTransactionManager;
import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.enums.Endpoint;
import info.unterrainer.commons.httpserver.enums.QueryField;
import info.unterrainer.commons.httpserver.exceptions.BadRequestException;
import info.unterrainer.commons.httpserver.interceptors.GetListInterceptorResult;
import info.unterrainer.commons.httpserver.interceptors.delegates.GetListInterceptor;
import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.JsonMapper;
import info.unterrainer.commons.serialization.exceptions.JsonMappingException;
import info.unterrainer.commons.serialization.exceptions.JsonProcessingException;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class GenericHandlerGroup<P extends BasicJpa, J extends BasicJson, E> implements HandlerGroup {

	private final BasicDao<P, E> dao;
	private final Class<P> jpaType;
	private final Class<J> jsonType;
	private final DaoTransactionManager<E> daoTransactionManager;
	private final JsonMapper jsonMapper;
	private final MapperFacade orikaMapper;
	private final String path;
	private final List<Endpoint> endpoints;
	private final List<GetListInterceptor> getListInterceptors;
	private final HandlerExtensions<P, J, E> extensions;
	private final ExecutorService executorService;
	private final HandlerUtils hu = new HandlerUtils();

	@Override
	public void addHandlers(final HttpServer server) {
		String p = path;
		if (!p.startsWith("/"))
			p = "/" + p;
		String pId = p + "/:" + QueryField.ID;
		if (endpoints.contains(Endpoint.ALL) || endpoints.contains(Endpoint.READONLY)
				|| endpoints.contains(Endpoint.GET_SINGLE))
			server.get(pId, this::getEntry);
		if (endpoints.contains(Endpoint.ALL) || endpoints.contains(Endpoint.READONLY)
				|| endpoints.contains(Endpoint.GET_LIST))
			server.get(p, this::getList);
		if (endpoints.contains(Endpoint.ALL) || endpoints.contains(Endpoint.CREATE))
			server.post(p, this::create);
		if (endpoints.contains(Endpoint.ALL) || endpoints.contains(Endpoint.UPDATE_FULL))
			server.put(pId, this::fullUpdate);
		if (endpoints.contains(Endpoint.ALL) || endpoints.contains(Endpoint.DELETE))
			server.delete(pId, this::delete);
	}

	private void getEntry(final Context ctx) {
		DaoTransaction<E> transaction = daoTransactionManager.beginTransaction();

		P jpa = hu.getJpaById(ctx, transaction.getManager(), dao);
		J json = orikaMapper.map(jpa, jsonType);
		json = extensions.runPostGetSingle(ctx, transaction.getManager(), jpa.getId(), jpa, json, executorService);

		transaction.end();
		ctx.attribute(Attribute.RESPONSE_OBJECT, json);
	}

	private void getList(final Context ctx) {
		Long offset = hu.getQueryParamAsLong(ctx, QueryField.PAGINATION_OFFSET, 0L);
		Long size = hu.getQueryParamAsLong(ctx, QueryField.PAGINATION_SIZE, Long.MAX_VALUE);

		GetListInterceptorResult interceptorResult = GetListInterceptorResult.builder()
				.selectClause("o")
				.joinClause("")
				.whereClause("")
				.params(null)
				.partOfQueryString("")
				.build();
		for (GetListInterceptor interceptor : getListInterceptors)
			try {
				GetListInterceptorResult result = interceptor.intercept(ctx, hu);
				if (result != null) {
					interceptorResult = result;
					break;
				}
			} catch (Exception e) {// NOOP}
			}

		DaoTransaction<E> transaction = daoTransactionManager.beginTransaction();

		ListJson<P> bList = dao.getList(transaction.getManager(), offset, size, interceptorResult.getSelectClause(),
				interceptorResult.getJoinClause(), interceptorResult.getWhereClause(), interceptorResult.getParams());
		ListJson<J> jList = new ListJson<>();
		for (P entry : bList.getEntries())
			jList.getEntries().add(orikaMapper.map(entry, jsonType));

		hu.setPaginationParamsFor(jList, offset, size, bList.getCount(), interceptorResult.getPartOfQueryString(), ctx);

		jList = extensions.runPostGetList(ctx, transaction.getManager(), size, offset, bList, jList, executorService);

		transaction.end();
		ctx.attribute(Attribute.RESPONSE_OBJECT, jList);
	}

	private void create(final Context ctx) throws IOException {
		String b = ctx.body();
		try {
			J json = jsonMapper.fromStringTo(jsonType, b);
			P mappedJpa = orikaMapper.map(json, jpaType);
			DaoTransaction<E> transaction = daoTransactionManager.beginTransaction();

			mappedJpa = extensions.runPreInsert(ctx, transaction.getManager(), json, mappedJpa, executorService);
			P createdJpa = dao.create(transaction.getManager(), mappedJpa);
			J r = orikaMapper.map(createdJpa, jsonType);

			r = extensions.runPostInsert(ctx, transaction.getManager(), json, mappedJpa, createdJpa, r,
					executorService);

			transaction.end();
			ctx.attribute(Attribute.RESPONSE_OBJECT, r);

		} catch (JsonProcessingException | JsonMappingException e) {
			throw new BadRequestException();
		}
	}

	private void fullUpdate(final Context ctx) throws IOException {
		P jpa = hu.getJpaById(ctx, dao);
		try {
			J json = jsonMapper.fromStringTo(jsonType, ctx.body());
			P mappedJpa = orikaMapper.map(json, jpaType);
			mappedJpa.setId(jpa.getId());
			mappedJpa.setCreatedOn(jpa.getCreatedOn());
			mappedJpa.setEditedOn(jpa.getEditedOn());
			DaoTransaction<E> transaction = daoTransactionManager.beginTransaction();

			mappedJpa = extensions.runPreModify(ctx, transaction.getManager(), jpa.getId(), json, jpa, mappedJpa,
					executorService);
			P persistedJpa = dao.update(mappedJpa);

			J r = orikaMapper.map(persistedJpa, jsonType);
			r = extensions.runPostModify(ctx, transaction.getManager(), jpa.getId(), json, jpa, mappedJpa, persistedJpa,
					r, executorService);

			transaction.end();
			ctx.attribute(Attribute.RESPONSE_OBJECT, r);

		} catch (JsonProcessingException | JsonMappingException e) {
			throw new BadRequestException();
		}
	}

	private void delete(final Context ctx) {
		ctx.attribute(Attribute.RESPONSE_OBJECT, null);
		Long id = hu.checkAndGetId(ctx);
		DaoTransaction<E> transaction = daoTransactionManager.beginTransaction();

		id = extensions.runPreDelete(ctx, transaction.getManager(), id, executorService);
		dao.delete(id);
		ctx.status(204);
		extensions.runPostDelete(ctx, transaction.getManager(), id, executorService);

		transaction.end();
	}
}
