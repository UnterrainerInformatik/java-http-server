package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.httpserver.extensions.AsyncExtensionContext;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;

public interface PreModifyAsync<P extends BasicJpa, J extends BasicJson> {

	/**
	 * Allows you to execute code before modification of an item.
	 * <p>
	 * Since this is asynchronous changing the DTOs will do nothing (runs in
	 * parallel and the action probably already happened when your code is
	 * executed).
	 * 
	 * @param asyncCtx     a context containing values that have been mapped using
	 *                     AsyncExtensionContextMappers
	 * @param receivedId   the ID of the item that is about to get modified
	 * @param receivedJson the JSON that was received in the HTTP call
	 * @param readJpa      the JPA that was read from the database based on the
	 *                     received ID
	 * @param resultJpa    the JPA that was mapped and is about to be modified
	 */
	void handle(AsyncExtensionContext asyncCtx, Long receivedId, J receivedJson, P readJpa, P resultJpa);
}
