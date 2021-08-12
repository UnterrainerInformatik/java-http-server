package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.httpserver.extensions.AsyncExtensionContext;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface PostDeleteAsync<P extends BasicJpa> {

	/**
	 * Allows you to execute code after the deletion of an item.
	 * <p>
	 * Since this is asynchronous changing the DTOs will do nothing (runs in
	 * parallel and the action probably already happened when your code is
	 * executed).
	 *
	 * @param asyncCtx   a context containing values that have been mapped using
	 *                   AsyncExtensionContextMappers
	 * @param receivedId the ID of the item that was deleted
	 * @param deletedJpa the JPA that was deleted
	 */
	void handle(AsyncExtensionContext asyncCtx, Long receivedId, P deletedJpa);
}
