package info.unterrainer.commons.httpserver.extensions.delegates;

import io.javalin.http.Context;

public interface PostDeleteSync {

	/**
	 * Allows you to execute code after the deletion of an item.<br/>
	 * You may change the ID with the return-value.
	 * <p/>
	 * Returning {@link false} will abort. So be sure to return something
	 * (status...) in that case using the {@link Context}.
	 *
	 * @param ctx        the Javalin context
	 * @param receivedId the ID of the item that was deleted
	 * @return true, if the handler-chain should be continued to be processed, false
	 *         otherwise.
	 */
	boolean handle(Context ctx, Long receivedId);
}
