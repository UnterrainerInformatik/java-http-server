package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;

public interface PostGetListAsync<P extends BasicJpa, J extends BasicJson> {

	/**
	 * Allows you to execute code after the fetching of an item.
	 * <p/>
	 * Since this is asynchronous changing the DTOs will do nothing (runs in
	 * parallel and the action probably already happened when your code is
	 * executed).
	 *
	 * @param receivedSize   the size-parameter of the list
	 * @param receivedOffset the offset-parameter of the list
	 * @param readList       the list that was read based on the size- and
	 *                       offset-parameters
	 * @param response       the JSON that will be sent as a response
	 */
	void handle(Long size, Long offset, ListJson<P> readList, ListJson<J> response);
}
