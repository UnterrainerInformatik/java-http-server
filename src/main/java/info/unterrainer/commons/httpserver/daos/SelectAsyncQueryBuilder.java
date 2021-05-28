package info.unterrainer.commons.httpserver.daos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;
import info.unterrainer.commons.rdbutils.enums.AsyncState;

public class SelectAsyncQueryBuilder<P extends BasicAsyncJpa, T> extends BasicSelectQueryBuilder<P, T, SelectAsyncQueryBuilder<P, T>>
		implements QueryInterface<P, T> {

	private Set<AsyncState> asyncStates = new HashSet<>();

	SelectAsyncQueryBuilder(final EntityManagerFactory emf, final JpqlAsyncDao<P> dao, final Class<T> resultType) {
		super(emf, dao, resultType);
	}

	@Override
	public SelectQuery<P, T> build() {
		return new SelectQuery<>(emf, this);
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
	public SelectAsyncQueryBuilder<P, T> asyncStateOf(final AsyncState... asyncStates) {
		this.asyncStates.addAll(Arrays.asList(asyncStates));
		return this;
	}

	@Override
	public TypedQuery<T> getTypedQuery(final EntityManager em) {
		return dao.getQuery(em, selectClause, joinClause, whereClause, parameters, resultType, orderByClause,
				lockPessimistic, asyncStates);
	}

	@Override
	public javax.persistence.Query getCountQuery(final EntityManager em) {
		return dao.getCountQuery(em, selectClause, joinClause, whereClause, parameters, asyncStates);
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public BasicJpqlDao<P> getDao() {
		return dao;
	}
}
