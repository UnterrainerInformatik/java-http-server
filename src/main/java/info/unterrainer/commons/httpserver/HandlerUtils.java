package info.unterrainer.commons.httpserver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import info.unterrainer.commons.httpserver.enums.QueryField;
import info.unterrainer.commons.httpserver.exceptions.BadRequestException;
import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.serialization.JsonMapper;
import io.javalin.http.Context;

public class HandlerUtils {

	public DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(JsonMapper.DATETIME_PATTERN);

	public <J> void setPaginationParamsFor(final ListJson<J> jList, final long offset, final long pageSize,
			final long count, final Context ctx) {

		jList.setFirst(String.format(QueryField.LIST_LINK, ctx.url(), 0, pageSize));

		if (pageSize > 0) {
			int last = (int) (count / pageSize * pageSize);
			if (last == count)
				last -= pageSize;
			jList.setLast(String.format(QueryField.LIST_LINK, ctx.url(), last, pageSize));
		}

		long next = offset + pageSize;
		if (next < count)
			jList.setNext(String.format(QueryField.LIST_LINK, ctx.url(), next, pageSize));

		long prev = offset - pageSize;
		if (prev < 0)
			prev = 0;
		jList.setPrevious(String.format(QueryField.LIST_LINK, ctx.url(), prev, pageSize));

		jList.setCount(count);
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
				result = LocalDateTime.parse(o, dateTimeFormatter);
			} catch (DateTimeParseException e) {
				throw new BadRequestException(String.format("Parameter %s has to be a dateTime of format '%s'", name,
						JsonMapper.DATETIME_PATTERN));
			}
		else if (mandatory)
			throw new BadRequestException(String.format("Parameter %s is mandatory", name));
		return result;
	}
}
