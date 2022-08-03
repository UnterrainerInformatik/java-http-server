package info.unterrainer.commons.httpserver.daos;

import java.time.LocalDateTime;
import java.util.function.Function;

import jakarta.persistence.EntityManager;

import info.unterrainer.commons.jreutils.DateUtils;
import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateQueryBuilder<P extends BasicJpa>
		extends BasicQueryEntityManagerBuilder<P, P, UpdateQueryBuilder<P>> {

	@Getter
	protected final BasicJpqlDao<P> dao;
	protected final P entity;

	protected <V> V withEntityManager(final Function<EntityManager, V> func) {
		if (entityManager == null)
			return Transactions.withNewTransactionReturning(dao.emf, em -> func.apply(em));
		return func.apply(entityManager);
	}

	/**
	 * Update the given entity.
	 *
	 * @return the entity after the update
	 */
	public P execute() {
		return withEntityManager(em -> {
			LocalDateTime time = DateUtils.nowUtc();
			entity.setEditedOn(time);
			return em.merge(entity);
		});
	}
}
