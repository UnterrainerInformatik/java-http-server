package info.unterrainer.commons.httpserver.interceptors;

import java.util.Map;

import info.unterrainer.commons.httpserver.HandlerUtils;
import info.unterrainer.commons.httpserver.daos.ParamMap;
import io.javalin.http.Context;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InterceptorParamBuilder {

	// userId=:userId AND (timeFrom<=:timeFrom OR timeTo>:timeTo)

	private final Context ctx;

	private final HandlerUtils hu = new HandlerUtils();

	private String selectClause;
	private String whereClause;
	private String joinClause;
	private Map<String, Object> params;
	private String queryString;

	public InterceptorParamBuilder builder(final Context ctx) {
		return new InterceptorParamBuilder(ctx);
	}

	public InterceptorParamBuilder select(final String selectClause) {
		this.selectClause = selectClause;
		return this;
	}

	public InterceptorParamBuilder join(final String joinClause) {
		this.joinClause = joinClause;
		return this;
	}

	public InterceptorParamBuilder andParameter(final String queryParamName) {
		addParam("AND", "o", "=", queryParamName);
		return this;
	}

	public InterceptorParamBuilder orParameter(final String queryParamName) {
		addParam("OR", "o", "=", queryParamName);
		return this;
	}

	private void addParam(final String operator, final String selector, final String comparison,
			final String queryParamName) {
		Long queryParamValue = hu.getQueryParamAsLong(ctx, queryParamName, null);
		if (queryParamValue != null) {
			if (!whereClause.isEmpty())
				whereClause += String.format(" %s", operator);
			whereClause += String.format(" %s.%s%s:%s", selector, queryParamName, comparison, queryParamName);
			params.put(queryParamName, queryParamValue);
			if (!queryString.isEmpty())
				queryString += "&";
			queryString += String.format("%s=%s", queryParamName, queryParamValue);
		}
	}

	public InterceptorData build() {
		ParamMap paramMap = new ParamMap(params);
		return new InterceptorData(selectClause, whereClause, joinClause, paramMap, queryString);
	}
}
