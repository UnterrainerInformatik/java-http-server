package info.unterrainer.commons.httpserver;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;

import info.unterrainer.commons.httpserver.daos.BasicDao;
import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.enums.Endpoint;
import info.unterrainer.commons.httpserver.enums.QueryField;
import info.unterrainer.commons.httpserver.exceptions.BadRequestException;
import info.unterrainer.commons.httpserver.exceptions.NotFoundException;
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
public class GenericHandlerGroup<P extends BasicJpa, J extends BasicJson> implements HandlerGroup {

	private final BasicDao<P> dao;
	private final Class<P> jpaType;
	private final Class<J> jsonType;
	private final JsonMapper jsonMapper;
	private final MapperFacade orikaMapper;
	private final String path;
	private final List<Endpoint> endpoints;
	private final List<GetListInterceptor> getListInterceptors;
	private final HandlerExtensions<P, J> extensions;
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

	public Long checkAndGetId(final Context ctx) {
		String s = ctx.pathParam(QueryField.ID);
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			throw new BadRequestException("Parameter has to be of numeric type (Long).");
		}
	}

	public P getJpaById(final Context ctx, final BasicDao<P> dao) {
		Long id = checkAndGetId(ctx);
		P jpa = dao.getById(id);
		if (jpa == null)
			throw new NotFoundException();
		return jpa;
	}

	private void getEntry(final Context ctx) {
		P jpa = getJpaById(ctx, dao);
		J json = orikaMapper.map(jpa, jsonType);
		json = extensions.runPostGetSingle(ctx, jpa.getId(), jpa, json, executorService);
		ctx.attribute(Attribute.RESPONSE_OBJECT, json);
	}

	private void getList(final Context ctx) {
		Long offset = hu.getQueryParamAsLong(ctx, QueryField.PAGINATION_OFFSET, 0L);
		Long size = hu.getQueryParamAsLong(ctx, QueryField.PAGINATION_SIZE, Long.MAX_VALUE);

		GetListInterceptorResult interceptorResult = GetListInterceptorResult.builder()
				.whereClause("")
				.params(null)
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

		ListJson<P> bList = dao.getList(offset, size, interceptorResult.getWhereClause(),
				interceptorResult.getParams());
		ListJson<J> jList = new ListJson<>();
		for (P entry : bList.getEntries())
			jList.getEntries().add(orikaMapper.map(entry, jsonType));

		hu.setPaginationParamsFor(jList, offset, size, bList.getCount(), ctx);

		jList = extensions.runPostGetList(ctx, size, offset, bList, jList, executorService);
		ctx.attribute(Attribute.RESPONSE_OBJECT, jList);
	}

	private void create(final Context ctx) throws IOException {
		String b = ctx.body();
		try {
			J json = jsonMapper.fromStringTo(jsonType, b);
			P mappedJpa = orikaMapper.map(json, jpaType);
			mappedJpa = extensions.runPreInsert(ctx, json, mappedJpa, executorService);
			P createdJpa = dao.create(mappedJpa);
			J r = orikaMapper.map(createdJpa, jsonType);

			r = extensions.runPostInsert(ctx, json, mappedJpa, createdJpa, r, executorService);
			ctx.attribute(Attribute.RESPONSE_OBJECT, r);

		} catch (JsonProcessingException | JsonMappingException e) {
			throw new BadRequestException();
		}
	}

	private void fullUpdate(final Context ctx) throws IOException {
		P jpa = getJpaById(ctx, dao);
		try {
			J json = jsonMapper.fromStringTo(jsonType, ctx.body());
			P mappedJpa = orikaMapper.map(json, jpaType);
			mappedJpa.setId(jpa.getId());
			mappedJpa.setCreatedOn(jpa.getCreatedOn());
			mappedJpa.setEditedOn(jpa.getEditedOn());
			mappedJpa = extensions.runPreModify(ctx, jpa.getId(), json, jpa, mappedJpa, executorService);
			P persistedJpa = dao.update(mappedJpa);

			J r = orikaMapper.map(persistedJpa, jsonType);
			r = extensions.runPostModify(ctx, jpa.getId(), json, jpa, mappedJpa, persistedJpa, r, executorService);
			ctx.attribute(Attribute.RESPONSE_OBJECT, r);

		} catch (JsonProcessingException | JsonMappingException e) {
			throw new BadRequestException();
		}
	}

	private void delete(final Context ctx) {
		ctx.attribute(Attribute.RESPONSE_OBJECT, null);
		Long id = checkAndGetId(ctx);
		id = extensions.runPreDelete(ctx, id, executorService);
		dao.delete(id);
		ctx.status(204);
		extensions.runPostDelete(ctx, id, executorService);
	}
}
