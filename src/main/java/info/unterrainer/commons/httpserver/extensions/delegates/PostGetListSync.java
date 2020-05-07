package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;

public interface PostGetListSync<P extends BasicJpa, J extends BasicJson> {

	/**
	 * Allows you to execute code after fetching of a list of items.
	 * <p/>
	 * Returning {@link null} will abort. So be sure to return something (status...)
	 * in that case using the {@link Context}.
	 *
	 * @param ctx            the Javalin context
	 * @param receivedSize   the size-parameter of the list
	 * @param receivedOffset the offset-parameter of the list
	 * @param readList       the list that was read based on the size- and
	 *                       offset-parameters
	 * @param response       the JSON that will be sent as a response
	 * @return the JSON that will be sent as a response
	 */
	ListJson<J> handle(Context ctx, Long size, Long offset, ListJson<P> readList, ListJson<J> response);
}
