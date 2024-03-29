package info.unterrainer.commons.httpserver.extensions.delegates;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import io.javalin.http.Context;

public interface PreDeleteSync<P extends BasicJpa, E> {

	/**
	 * Allows you to execute code before the deletion of an item.<br>
	 * You may change the ID with the return-value.
	 * <p>
	 * Returning {@code null} will abort. So be sure to return something (status...)
	 * in that case using the {@link Context}.
	 *
	 * @param ctx           the Javalin context
	 * @param entityManager the entity-manager you can use to get the active
	 *                      transaction, if any
	 * @param receivedId    the ID of the item that is about to get deleted
	 * @param jpaToDelete   the JPA to delete
	 * @return the ID of the item that is about to get deleted
	 */
	Long handle(Context ctx, E entityManager, Long receivedId, P jpaToDelete);
}
