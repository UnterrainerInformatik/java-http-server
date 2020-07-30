package info.unterrainer.commons.httpserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import info.unterrainer.commons.httpserver.daos.BasicDao;
import info.unterrainer.commons.httpserver.enums.QueryField;
import info.unterrainer.commons.httpserver.exceptions.BadRequestException;
import info.unterrainer.commons.httpserver.exceptions.NotFoundException;
import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import io.javalin.http.Context;

public class HandlerUtils {

	public Long checkAndGetId(final Context ctx) {
		String s = ctx.pathParam(QueryField.ID);
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			throw new BadRequestException("Parameter has to be of numeric type (Long).");
		}
	}

	public <P extends BasicJpa, E> P getJpaById(final Context ctx, final E entityManager, final BasicDao<P, E> dao) {
		Long id = checkAndGetId(ctx);
		P jpa = dao.getById(entityManager, id);
		if (jpa == null)
			throw new NotFoundException();
		return jpa;
	}

	public <P extends BasicJpa, E> P getJpaById(final Context ctx, final BasicDao<P, E> dao) {
		Long id = checkAndGetId(ctx);
		P jpa = dao.getById(id);
		if (jpa == null)
			throw new NotFoundException();
		return jpa;
	}

	public <J> void setPaginationParamsFor(final ListJson<J> jList, final long offset, final long pageSize,
			final long count, final String additionalQueryParamsString, final Context ctx) {

		String addition = "&" + sanitizeAdditionalParamsString(additionalQueryParamsString);

		jList.setFirst(String.format(QueryField.LIST_LINK, ctx.url(), 0, pageSize, addition));

		if (pageSize > 0) {
			int last = (int) (count / pageSize * pageSize);
			if (last == count)
				last -= pageSize;
			jList.setLast(String.format(QueryField.LIST_LINK, ctx.url(), last, pageSize, addition));
		}

		long next = offset + pageSize;
		if (next < count)
			jList.setNext(String.format(QueryField.LIST_LINK, ctx.url(), next, pageSize, addition));

		long prev = offset - pageSize;
		if (prev < 0)
			prev = 0;
		jList.setPrevious(String.format(QueryField.LIST_LINK, ctx.url(), prev, pageSize, addition));

		jList.setCount(count);
	}

	private String sanitizeAdditionalParamsString(final String additionalQueryParamString) {
		String addition = additionalQueryParamString;
		while (addition.startsWith("?"))
			addition = addition.substring(1);
		while (addition.startsWith("&"))
			addition = addition.substring(1);
		return addition;
	}

	public Long getQueryParamAsLong(final Context ctx, final String name) {
		return getQueryParamAsLong(ctx, name, true, null);
	}

	public Long getQueryParamAsLong(final Context ctx, final String name, final Long defaultValue) {
		return getQueryParamAsLong(ctx, name, false, defaultValue);
	}

	private Long getQueryParamAsLong(final Context ctx, final String name, final boolean mandatory,
			final Long defaultValue) {
		String o = ctx.queryParam(name);
		Long result = defaultValue;
		if (o != null)
			try {
				result = Long.parseLong(o);
			} catch (NumberFormatException e) {
				throw new BadRequestException(String.format("Parameter %s has to be a number", name));
			}
		else if (mandatory)
			throw new BadRequestException(String.format("Parameter %s is mandatory", name));
		return result;
	}

	public String getQueryParamAsString(final Context ctx, final String name) {
		return getQueryParamAsString(ctx, name, true, null);
	}

	public String getQueryParamAsString(final Context ctx, final String name, final String defaultValue) {
		return getQueryParamAsString(ctx, name, false, defaultValue);
	}

	private String getQueryParamAsString(final Context ctx, final String name, final boolean mandatory,
			final String defaultValue) {
		String o = ctx.queryParam(name);
		String result = defaultValue;
		if (o != null)
			result = o;
		else if (mandatory)
			throw new BadRequestException(String.format("Parameter %s is mandatory", name));
		return result;
	}

	public LocalDateTime getQueryParamAsDateTime(final Context ctx, final String name) {
		return getQueryParamAsDateTime(ctx, name, true, null);
	}

	public LocalDateTime getQueryParamAsDateTime(final Context ctx, final String name,
			final LocalDateTime defaultValue) {
		return getQueryParamAsDateTime(ctx, name, false, defaultValue);
	}

	private LocalDateTime getQueryParamAsDateTime(final Context ctx, final String name, final boolean mandatory,
			final LocalDateTime defaultValue) {
		String o = ctx.queryParam(name);
		LocalDateTime result = defaultValue;

		if (o != null)
			try {
				if (o.endsWith("Z") || o.endsWith("z"))
					o = o.substring(0, o.length() - 1);
				result = LocalDateTime.parse(o, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
			} catch (DateTimeParseException e) {
				throw new BadRequestException(String.format("Parameter %s has to be a dateTime of format '%s'", name,
						DateTimeFormatter.ISO_LOCAL_DATE_TIME.toString()));
			}
		else if (mandatory)
			throw new BadRequestException(String.format("Parameter %s is mandatory", name));
		return result;
	}
}
