package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class QueryBuilder<P extends BasicJpa, T> extends BasicQueryBuilder<P, T, QueryBuilder<P, T>> {

	private final Class<T> resultType;

	QueryBuilder(final EntityManagerFactory emf, final JpqlDao<P> dao, final Class<T> resultType) {
		super(emf, dao);
		this.resultType = resultType;
	}

	public Query<P, T> build() {
		TypedQuery<T> q;
		if (entityManager != null) {
			q = dao.getQuery(entityManager, selectClause, joinClause, whereClause, parameters, resultType,
					orderByClause, lockPessimistic);
			javax.persistence.Query cq = new CountQueryBuilder<P, T>(emf, dao).entityManager(entityManager)
					.select(selectClause)
					.join(joinClause)
					.where(whereClause)
					.build();
			return new Query<>(dao, q, cq);
		} else {
			q = Transactions.withNewTransactionReturning(emf, em -> dao.getQuery(entityManager, selectClause,
					joinClause, whereClause, parameters, resultType, orderByClause, lockPessimistic));
			javax.persistence.Query cq = new CountQueryBuilder<P, T>(emf, dao).entityManager(entityManager)
					.select(selectClause)
					.join(joinClause)
					.where(whereClause)
					.build();
			return new Query<>(dao, q, cq);
		}
	}
}
