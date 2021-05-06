package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class CountQueryBuilder<P extends BasicJpa, T> extends BasicQueryBuilder<P, T, CountQueryBuilder<P, T>> {

	CountQueryBuilder(final EntityManagerFactory emf, final JpqlDao<P> dao) {
		super(emf, dao);
	}

	public Query build() {
		if (entityManager != null)
			return dao.getCountQuery(entityManager, selectClause, joinClause, whereClause, parameters);
		return Transactions.withNewTransactionReturning(emf,
				em -> dao.getCountQuery(em, selectClause, joinClause, whereClause, parameters));
	}
}
