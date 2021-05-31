package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class JpaListQuery<P extends BasicJpa> extends ListQuery<P, P> {

	JpaListQuery(final EntityManagerFactory emf, final QueryInterface<P, P> builder) {
		super(emf, builder);
	}

	/**
	 * Gets the first row of the result-set and updates it with the values of the
	 * given JPA. If the result-set was empty, an insert is performed with the given
	 * JPA. This all happens in the same transaction or within the transaction
	 * you've passed by specifying an {@link EntityManager} in the query.
	 *
	 * @param entity the entity to update with, or create.
	 * @return an {@link UpdateResult} that will tell you if there was an insert or
	 *         update, and if it was an update, what the old values were
	 */
	public UpsertResult<P> upsert(final P entity) {
		return withEntityManager(em -> builder.getDao()._upsert(em, builder.getTypedQuery(em), entity));
	}
}
