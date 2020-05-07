package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;

public interface PostGetSingleAsync<P extends BasicJpa, J extends BasicJson> {

	/**
	 * Allows you to execute code after the fetching of a list of items.
	 * <p/>
	 * Since this is asynchronous changing the DTOs will do nothing (runs in
	 * parallel and the action probably already happened when your code is
	 * executed).
	 *
	 * @param receivedId the ID of the item that was fetched
	 * @param readJpa    the JPA that was read based on the received ID
	 * @param response   the JSON that will be sent as a response
	 */
	void handle(Long receivedId, P readJpa, J response);
}
