package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;

public interface PreModifySync<P extends BasicJpa, J extends BasicJson> {

	/**
	 * Allows you to execute code before modification of an item.
	 * <p/>
	 * Returning {@link null} will abort. So be sure to return something (status...)
	 * in that case using the {@link Context}.
	 *
	 * @param ctx          the Javalin context
	 * @param receivedId   the ID of the item that is about to get modified
	 * @param receivedJson the JSON that was received in the HTTP call
	 * @param readJpa      the JPA that was read from the database based on the
	 *                     received ID
	 * @param mappedJpa    the JPA that was mapped and is about to be modified
	 * @return the modified JPA that will be persisted
	 */
	P handle(Context ctx, Long receivedId, J receivedJson, P readJpa, P mappedJpa);
}
