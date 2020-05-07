package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;

public interface PostModifyAsync<P extends BasicJpa, J extends BasicJson> {

	/**
	 * Allows you to execute code after the modification of an item.
	 * <p/>
	 * Since this is asynchronous changing the DTOs will do nothing (runs in
	 * parallel and the action probably already happened when your code is
	 * executed).
	 *
	 * @param receivedId   the ID of the item that was modified
	 * @param receivedJson the JSON that was received in the HTTP call
	 * @param readJpa      the JPA that was read from the database based on the
	 *                     received ID
	 * @param mappedJpa    the JPA that was modified
	 * @param persistedJpa the JPA that was returned from the database when saving
	 * @param response     the JSON that will be sent as a response
	 */
	void handle(Long receivedId, J receivedJson, P readJpa, P mappedJpa, P persistedJpa, J response);
}
