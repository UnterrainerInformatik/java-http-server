package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;

public interface PostInsertSync<P extends BasicJpa, J extends BasicJson, E> {

	/**
	 * Allows you to execute code after the insertion of an item.
	 * <p>
	 * Returning {@code null} will abort. So be sure to return something (status...)
	 * in that case using the {@link Context}.
	 *
	 * @param ctx           the Javalin context
	 * @param entityManager the entity-manager you can use to get the active
	 *                      transaction, if any
	 * @param receivedJson  the JSON that was received in the HTTP call
	 * @param mappedJpa     the JPA that was inserted
	 * @param createdJpa    the JPA that was created in (returned from) the database
	 * @param response      the JSON that will be sent as a response
	 * @return the JSON that will be sent as a response
	 */
	J handle(Context ctx, E entityManager, J receivedJson, P mappedJpa, P createdJpa, J response);
}
