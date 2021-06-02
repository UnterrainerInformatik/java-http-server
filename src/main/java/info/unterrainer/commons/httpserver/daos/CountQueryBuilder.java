package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class CountQueryBuilder<P extends BasicJpa> extends BasicListQueryBuilder<P, Long, CountQueryBuilder<P>> {

	CountQueryBuilder(final EntityManagerFactory emf, final JpqlDao<P> dao) {
		super(emf, dao, Long.class);
	}

	public Long build() {
		if (entityManager != null)
			return (Long) dao.coreDao
					.getCountQuery(entityManager, selectClause, joinClause, whereClause, parameters, null, tenantIds)
					.getSingleResult();
		return (Long) Transactions.withNewTransactionReturning(emf,
				em -> dao.coreDao.getCountQuery(em, selectClause, joinClause, whereClause, parameters, null, tenantIds)
						.getSingleResult());
	}
}
