package info.unterrainer.commons.httpserver.interceptors.delegates;

import info.unterrainer.commons.httpserver.HandlerUtils;
import info.unterrainer.commons.httpserver.daos.ParamMap;
import info.unterrainer.commons.httpserver.interceptors.InterceptorData;
import io.javalin.http.Context;

public interface GetListInterceptor {

	/**
	 * Allows you to execute intercept the path to the get-list method currently
	 * being built.
	 * <p>
	 * Returning {@code null} or throwing an exception will abort the evaluation of
	 * this interceptor. If you'd like your interceptor to be taken by the server,
	 * return a valid {@link InterceptorData}
	 *
	 * @param ctx          the Javalin context
	 * @param handlerUtils a {@link HandlerUtils} helper object that aids you in
	 *                     reading and evaluating parameters
	 * @return a {@link InterceptorData} containing the whereClause and
	 *         {@link ParamMap} to be used in the following select-statement done by
	 *         the server
	 */
	InterceptorData intercept(Context ctx, HandlerUtils handlerUtils);
}
