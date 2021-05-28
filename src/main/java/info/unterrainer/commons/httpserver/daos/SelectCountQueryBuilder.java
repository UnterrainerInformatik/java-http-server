package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class SelectCountQueryBuilder<P extends BasicJpa> extends BasicSelectQueryBuilder<P, Long, SelectCountQueryBuilder<P>> {

	SelectCountQueryBuilder(final EntityManagerFactory emf, final JpqlDao<P> dao) {
		super(emf, dao, Long.class);
	}

	public Long build() {
		if (entityManager != null)
			return (Long) dao.getCountQuery(entityManager, selectClause, joinClause, whereClause, parameters, null)
					.getSingleResult();
		return (Long) Transactions.withNewTransactionReturning(emf,
				em -> dao.getCountQuery(em, selectClause, joinClause, whereClause, parameters, null).getSingleResult());
	}
}
