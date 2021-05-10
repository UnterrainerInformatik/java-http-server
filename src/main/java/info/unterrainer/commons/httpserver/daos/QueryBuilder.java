package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class QueryBuilder<P extends BasicJpa, T> extends BasicQueryBuilder<P, T, QueryBuilder<P, T>> {

	QueryBuilder(final EntityManagerFactory emf, final JpqlDao<P> dao, final Class<T> resultType) {
		super(emf, dao, resultType);
	}

	public Query<P, T> build() {
		return new Query<>(emf, this);
	}

	TypedQuery<T> getTypedQuery(final EntityManager em) {
		return dao.getQuery(em, selectClause, joinClause, whereClause, parameters, resultType, orderByClause,
				lockPessimistic, null);
	}

	javax.persistence.Query getCountQuery(final EntityManager em) {
		return dao.getCountQuery(em, selectClause, joinClause, whereClause, parameters, null);
	}
}
