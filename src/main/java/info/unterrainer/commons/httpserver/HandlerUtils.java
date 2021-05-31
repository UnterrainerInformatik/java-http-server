package info.unterrainer.commons.httpserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import info.unterrainer.commons.httpserver.daos.CoreDao;
import info.unterrainer.commons.httpserver.daos.DaoTransaction;
import info.unterrainer.commons.httpserver.enums.QueryField;
import info.unterrainer.commons.httpserver.exceptions.BadRequestException;
import info.unterrainer.commons.httpserver.exceptions.NotFoundException;
import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import io.javalin.http.Context;

public class HandlerUtils {

	public Long checkAndGetId(final Context ctx) {
		String s = ctx.pathParam(QueryField.ID);
		return convertToLong(s);
	}

	public <P extends BasicJpa, E> P getJpaById(final Context ctx, final E manager, final CoreDao<P, E> dao) {
		Long id = checkAndGetId(ctx);
		P jpa = dao.getById(manager, id);
		if (jpa == null)
			throw new NotFoundException();
		return jpa;
	}

	public <P extends BasicJpa, E> P getJpaById(final Context ctx, final CoreDao<P, E> dao) {
		Long id = checkAndGetId(ctx);
		DaoTransaction<E> transaction = dao.getTransactionManager().beginTransaction();
		try {
			P jpa = dao.getById(transaction.getManager(), id);
			if (jpa == null)
				throw new NotFoundException();
			return jpa;
		} finally {
			transaction.end();
		}
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
			result = convertToLong(o);
		else if (mandatory)
			throw new BadRequestException(String.format("Parameter %s is mandatory", name));
		return result;
	}

	public long convertToLong(final String s) {
		try {
			return Long.parseLong(s);
		} catch (NumberFormatException e) {
			throw new BadRequestException("Parameter has to be of a numeric type.");
		}
	}

	public float convertToFloat(final String s) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException e) {
			throw new BadRequestException("Parameter has to be of a numeric type.");
		}
	}

	public double convertToDouble(final String s) {
		try {
			return Double.parseDouble(s);
		} catch (NumberFormatException e) {
			throw new BadRequestException("Parameter has to be of a numeric type.");
		}
	}

	public int convertToInt(final String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			throw new BadRequestException("Parameter has to be of a numeric type.");
		}
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
			result = convertToLocalDateTime(o);
		else if (mandatory)
			throw new BadRequestException(String.format("Parameter %s is mandatory", name));
		return result;
	}

	public LocalDateTime convertToLocalDateTime(String o) {
		try {
			if (o.endsWith("Z") || o.endsWith("z"))
				o = o.substring(0, o.length() - 1);
			return LocalDateTime.parse(o, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		} catch (DateTimeParseException e) {
			throw new BadRequestException(String.format("Parameter has to be a dateTime of format '%s'",
					DateTimeFormatter.ISO_LOCAL_DATE_TIME.toString()));
		}
	}

	public Boolean getQueryParamAsBoolean(final Context ctx, final String name) {
		return getQueryParamAsBoolean(ctx, name, true, null);
	}

	public Boolean getQueryParamAsBoolean(final Context ctx, final String name, final Boolean defaultValue) {
		return getQueryParamAsBoolean(ctx, name, false, defaultValue);
	}

	private Boolean getQueryParamAsBoolean(final Context ctx, final String name, final boolean mandatory,
			final Boolean defaultValue) {
		String o = ctx.queryParam(name);
		Boolean result = defaultValue;

		if (o != null)
			return convertToBoolean(o);
		else if (mandatory)
			throw new BadRequestException(String.format("Parameter %s is mandatory", name));
		return result;
	}

	public boolean convertToBoolean(final String o) {
		if (o == null)
			return false;

		String v = o.strip().toLowerCase();
		return !v.equals("false") || !v.equals("no") || !v.equals("0");
	}
}
