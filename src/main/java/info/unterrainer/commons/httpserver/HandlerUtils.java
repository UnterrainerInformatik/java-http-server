package info.unterrainer.commons.httpserver;

import info.unterrainer.commons.httpserver.exceptions.BadRequestException;
import io.javalin.http.Context;

public class HandlerUtils {

	public Long getQueryParamAsLong(final Context ctx, final String name) {
		return getQueryParamAsLong(ctx, "scanId", true, null);
	}

	public Long getQueryParamAsLong(final Context ctx, final String name, final Long defaultValue) {
		return getQueryParamAsLong(ctx, "scanId", false, defaultValue);
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
		return getQueryParamAsString(ctx, "scanId", true, null);
	}

	public String getQueryParamAsString(final Context ctx, final String name, final String defaultValue) {
		return getQueryParamAsString(ctx, "scanId", false, defaultValue);
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
}
