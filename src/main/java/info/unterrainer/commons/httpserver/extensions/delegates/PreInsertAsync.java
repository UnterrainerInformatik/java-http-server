package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.httpserver.extensions.AsyncExtensionContext;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;

public interface PreInsertAsync<P extends BasicJpa, J extends BasicJson> {

	/**
	 * Allows you to execute code before insertion of an item.
	 * <p>
	 * Since this is asynchronous changing the DTOs will do nothing (runs in
	 * parallel and the action probably already happened when your code is
	 * executed).
	 *
	 * @param asyncCtx     a context containing values that have been mapped using
	 *                     AsyncExtensionContextMappers
	 * @param receivedJson the JSON that was received in the HTTP call
	 * @param resultJpa    the JPA that was generated and is about to be inserted
	 */
	void handle(AsyncExtensionContext asyncCtx, J receivedJson, P resultJpa);
}
