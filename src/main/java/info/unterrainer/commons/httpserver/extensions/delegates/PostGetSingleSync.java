package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;

public interface PostGetSingleSync<P extends BasicJpa, J extends BasicJson, E> {

	/**
	 * Allows you to execute code after fetching of an item.
	 * <p>
	 * Returning {@code null} will abort. So be sure to return something (status...)
	 * in that case using the {@link Context}.
	 *
	 * @param ctx           the Javalin context
	 * @param entityManager the entity-manager you can use to get the active
	 *                      transaction, if any
	 * @param receivedId    the ID of the item that was fetched
	 * @param readJpa       the JPA that was read based on the received ID
	 * @param response      the JSON that will be sent as a response
	 * @return the JSON that will be sent as a response
	 */
	J handle(Context ctx, E entityManager, Long receivedId, P readJpa, J response);
}
