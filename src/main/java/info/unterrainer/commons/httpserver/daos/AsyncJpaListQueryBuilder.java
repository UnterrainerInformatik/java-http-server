package info.unterrainer.commons.httpserver.daos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;
import info.unterrainer.commons.rdbutils.enums.AsyncState;

public class AsyncJpaListQueryBuilder<P extends BasicAsyncJpa>
		extends BasicListQueryBuilder<P, P, AsyncJpaListQueryBuilder<P>> implements QueryInterface<P, P> {

	private Set<AsyncState> asyncStates = new HashSet<>();

	AsyncJpaListQueryBuilder(final EntityManagerFactory emf, final AsyncJpqlDao<P> dao, final Class<P> resultType) {
		super(emf, dao, resultType);
	}

	@Override
	public TypedQuery<P> getTypedQuery(final EntityManager em) {
		return dao.coreDao.getQuery(em, selectClause, joinClause, whereClause, parameters, resultType, orderByClause,
				lockPessimistic, asyncStates, readTenantIds);
	}

	@Override
	public jakarta.persistence.Query getCountQuery(final EntityManager em) {
		return dao.coreDao.getCountQuery(em, selectClause, joinClause, whereClause, parameters, asyncStates,
				readTenantIds);
	}

	public JpaListQuery<P> build() {
		return new JpaListQuery<>(emf, this);
	}

	/**
	 * Adds a single or multiple OR-clauses containing the specified
	 * {@link AsyncState}s.
	 * <p>
	 * For example calling
	 * {@code .whereStateOf(AsyncState.NEW, AsyncState.PROCESSING)} will result in
	 * the where-clause
	 * {@code (..rest of where clause..) AND (state = 'NEW' OR state = 'PROCESSING').}
	 * <p>
	 * Repetitive calling of this method just adds all the AsyncState values
	 * (doesn't clear the collection).
	 *
	 * @param asyncStates a single or number of AsyncState values
	 * @return an instance of this builder to provide a fluent interface
	 */
	public AsyncJpaListQueryBuilder<P> asyncStateOf(final AsyncState... asyncStates) {
		this.asyncStates.addAll(Arrays.asList(asyncStates));
		return this;
	}
}
