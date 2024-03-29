package info.unterrainer.commons.httpserver.daos;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import jakarta.persistence.EntityManager;

import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SingleQueryBuilder<P extends BasicJpa, T>
		extends BasicQueryEntityManagerBuilder<P, T, SingleQueryBuilder<P, T>> {

	@Getter
	protected final BasicJpqlDao<P> dao;
	protected final Long id;

	protected <V> V withEntityManager(final Function<EntityManager, V> func) {
		if (entityManager == null)
			return Transactions.withNewTransactionReturning(dao.emf, em -> func.apply(em));
		return func.apply(entityManager);
	}

	/**
	 * Get the selected entity.
	 *
	 * @return the selected entity
	 */
	public P get() {
		return withEntityManager(em -> {
			List<P> resultList = dao.coreDao
					.getQuery(em, "o", null, "o.id = :id", Map.of("id", id), dao.type, null, false, null, readTenantIds)
					.getResultList();
			if (resultList.isEmpty())
				return null;
			return resultList.get(0);
		});
	}

	/**
	 * Get the selected entity or throw an exception if it wasn't found.
	 *
	 * @return the selected entity
	 */
	public P getOrException() {
		return withEntityManager(em -> dao.coreDao
				.getQuery(em, "o", null, "o.id = :id", Map.of("id", id), dao.type, null, false, null, readTenantIds)
				.getSingleResult());
	}

	/**
	 * Delete the selected entity.
	 */
	public void delete() {
		withEntityManager(em -> {
			dao.coreDao.delete(em, id, readTenantIds);
			return null;
		});
	}
}
