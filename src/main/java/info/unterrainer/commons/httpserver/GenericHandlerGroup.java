package info.unterrainer.commons.httpserver;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;

import info.unterrainer.commons.httpserver.daos.BasicDao;
import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.enums.Endpoint;
import info.unterrainer.commons.httpserver.enums.HttpMethod;
import info.unterrainer.commons.httpserver.enums.Position;
import info.unterrainer.commons.httpserver.enums.QueryField;
import info.unterrainer.commons.httpserver.exceptions.BadRequestException;
import info.unterrainer.commons.httpserver.exceptions.NotFoundException;
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
	private final GenericHandlerAddons<P, J> extensions;
	private final ExecutorService executorService;

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

	public void setPaginationParamsFor(final ListJson<J> jList, final int offset, final int pageSize, final long count,
			final Context ctx) {

		jList.setFirst(String.format(QueryField.LIST_LINK, ctx.url(), 0, pageSize));

		if (pageSize > 0) {
			int last = (int) (count / pageSize * pageSize);
			if (last == count)
				last -= pageSize;
			jList.setLast(String.format(QueryField.LIST_LINK, ctx.url(), last, pageSize));
		}

		int next = offset + pageSize;
		if (next < count)
			jList.setNext(String.format(QueryField.LIST_LINK, ctx.url(), next, pageSize));

		int prev = offset - pageSize;
		if (prev < 0)
			prev = 0;
		jList.setPrevious(String.format(QueryField.LIST_LINK, ctx.url(), prev, pageSize));

		jList.setCount(count);
	}

	private Integer parseListParam(final String name, final Integer defaultValue, final Context ctx) {
		String o = ctx.queryParam(name);
		Integer result = defaultValue;
		if (o != null)
			try {
				result = Integer.parseInt(o);
			} catch (NumberFormatException e) {
				throw new BadRequestException(String.format("Parameter %s has to be a number", name));
			}
		return result;
	}

	private void getEntry(final Context ctx) {
		if (!extensions.runHandlers(ctx, Position.BEFORE, HttpMethod.GET_SINGLE, executorService, null, null, null))
			return;

		P jpa = getJpaById(ctx, dao);
		J json = orikaMapper.map(jpa, jsonType);

		if (!extensions.runHandlers(ctx, Position.AFTER, HttpMethod.GET_SINGLE, executorService, jpa, json, null))
			return;

		ctx.attribute(Attribute.RESPONSE_OBJECT, json);
	}

	private void getList(final Context ctx) {
		if (!extensions.runHandlers(ctx, Position.BEFORE, HttpMethod.GET_LIST, executorService, null, null, null))
			return;

		Integer offset = parseListParam(QueryField.PAGINATION_OFFSET, 0, ctx);
		Integer size = parseListParam(QueryField.PAGINATION_SIZE, Integer.MAX_VALUE, ctx);

		ListJson<P> bList = dao.getList(offset, size);
		ListJson<J> jList = new ListJson<>();
		for (P entry : bList.getEntries())
			jList.getEntries().add(orikaMapper.map(entry, jsonType));

		setPaginationParamsFor(jList, offset, size, bList.getCount(), ctx);

		if (!extensions.runHandlers(ctx, Position.AFTER, HttpMethod.GET_LIST, executorService, null, null, jList))
			return;

		ctx.attribute(Attribute.RESPONSE_OBJECT, jList);
	}

	private void create(final Context ctx) throws IOException {
		if (!extensions.runHandlers(ctx, Position.BEFORE, HttpMethod.POST, executorService, null, null, null))
			return;

		String b = ctx.body();
		try {
			J json = jsonMapper.fromStringTo(jsonType, b);
			P mappedJpa = orikaMapper.map(json, jpaType);
			LocalDateTime time = LocalDateTime.now();
			mappedJpa.setCreatedOn(time);
			mappedJpa.setEditedOn(time);
			P createdJpa = dao.create(mappedJpa);
			J r = orikaMapper.map(createdJpa, jsonType);

			if (!extensions.runHandlers(ctx, Position.AFTER, HttpMethod.POST, executorService, mappedJpa, json, null))
				return;

			ctx.attribute(Attribute.RESPONSE_OBJECT, r);

		} catch (JsonProcessingException | JsonMappingException e) {
			throw new BadRequestException();
		}
	}

	private void fullUpdate(final Context ctx) throws IOException {
		if (!extensions.runHandlers(ctx, Position.BEFORE, HttpMethod.PUT, executorService, null, null, null))
			return;

		P jpa = getJpaById(ctx, dao);
		try {
			J json = jsonMapper.fromStringTo(jsonType, ctx.body());
			P mappedJpa = orikaMapper.map(json, jpaType);
			mappedJpa.setEditedOn(LocalDateTime.now());
			mappedJpa.setCreatedOn(jpa.getCreatedOn());
			mappedJpa.setId(jpa.getId());
			dao.persist(mappedJpa);
			ctx.status(204);

			if (!extensions.runHandlers(ctx, Position.AFTER, HttpMethod.PUT, executorService, mappedJpa, json, null))
				return;

		} catch (JsonProcessingException | JsonMappingException e) {
			throw new BadRequestException();
		}
	}

	private void delete(final Context ctx) {
		if (!extensions.runHandlers(ctx, Position.BEFORE, HttpMethod.DELETE, executorService, null, null, null))
			return;

		ctx.attribute(Attribute.RESPONSE_OBJECT, null);
		Long id = checkAndGetId(ctx);
		dao.delete(id);
		ctx.status(204);

		if (!extensions.runHandlers(ctx, Position.AFTER, HttpMethod.DELETE, executorService, null, null, null))
			return;
	}
}
