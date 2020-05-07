package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;

public interface PostModifySync<P extends BasicJpa, J extends BasicJson> {

	/**
	 * Allows you to execute code after the modification of an item.
	 * <p/>
	 * Returning {@link null} will abort. So be sure to return something (status...)
	 * in that case using the {@link Context}.
	 *
	 * @param ctx          the Javalin context
	 * @param receivedId   the ID of the item that was modified
	 * @param receivedJson the JSON that was received in the HTTP call
	 * @param readJpa      the JPA that was read from the database based on the
	 *                     received ID
	 * @param mappedJpa    the JPA that was modified
	 * @param response     the JSON that will be sent as a response
	 * @return the JSON that will be sent as a response
	 */
	J handle(Context ctx, Long receivedId, J receivedJson, P readJpa, P mappedJpa, J response);
}
