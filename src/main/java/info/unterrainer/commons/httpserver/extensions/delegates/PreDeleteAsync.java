package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.httpserver.extensions.AsyncExtensionContext;

public interface PreDeleteAsync {

	/**
	 * Allows you to execute code before deletion of an item.
	 * <p>
	 * Since this is asynchronous changing the DTOs will do nothing (runs in
	 * parallel and the action probably already happened when your code is
	 * executed).
	 * 
	 * @param asyncCtx   a context containing values that have been mapped using
	 *                   AsyncExtensionContextMappers
	 * @param receivedId the ID of the item that is about to get deleted
	 */
	void handle(AsyncExtensionContext asyncCtx, Long receivedId);
}
