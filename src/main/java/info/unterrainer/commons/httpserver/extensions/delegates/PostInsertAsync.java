package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;

public interface PostInsertAsync<P extends BasicJpa, J extends BasicJson> {

	/**
	 * Allows you to execute code after the insertion of an item.
	 * <p>
	 * Since this is asynchronous changing the DTOs will do nothing (runs in
	 * parallel and the action probably already happened when your code is
	 * executed).
	 *
	 * @param receivedJson the JSON that was received in the HTTP call
	 * @param mappedJpa    the JPA that was inserted
	 * @param createdJpa   the JPA that was created in (returned from) the database
	 * @param response     the JSON that will be sent as a response
	 */
	void handle(J receivedJson, P mappedJpa, P createdJpa, J response);
}
