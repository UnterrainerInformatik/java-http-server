package info.unterrainer.commons.httpserver.daos;

import java.util.List;

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
	 * @return an {@link UpsertResult} that will tell you if there was an insert or
	 *         update, and if it was an update, what the old values were
	 */
	public UpsertResult<P> upsert(final P entity) {
		return withEntityManager(
				em -> builder.getDao().upsert(em, builder.getTypedQuery(em), entity, builder.getTenantIds()));
	}

	/**
	 * Delete the selected items from the database one at a time.
	 * <p>
	 * As this will NOT result in a real SQL delete-query, and it doesn't really
	 * care about your SELECT or ORDER BY clauses as well as your locking (is always
	 * a write-lock).
	 */
	public void delete() {
		withEntityManager(em -> {
			List<P> list = builder.getDao().getList(em, builder.getTypedQuery(em), 0, Long.MAX_VALUE);
			for (P jpa : list)
				builder.getDao().coreDao.delete(em, jpa.getId(), builder.getTenantIds());
			return null;
		});
	}
}
