package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;

public interface PreInsertSync<P extends BasicJpa, J extends BasicJson, E> {

	/**
	 * Allows you to execute code before insertion of an item.
	 * <p/>
	 * Returning {@link null} will abort. So be sure to return something (status...)
	 * in that case using the {@link Context}.
	 *
	 * @param ctx           the Javalin context
	 * @param entityManager the entity-manager you can use to get the active
	 *                      transaction, if any
	 * @param receivedJson  the JSON that was received in the HTTP call
	 * @param resultJpa     the JPA that was generated and is about to be inserted
	 * @return the JPA that will be inserted
	 */
	P handle(Context ctx, E entityManager, J receivedJson, P resultJpa);
}
