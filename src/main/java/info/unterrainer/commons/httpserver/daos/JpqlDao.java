package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class JpqlDao<P extends BasicJpa> extends BasicJpqlDao<P> implements BasicDao<P, EntityManager> {

	/**
	 * Generates a DAO that lets you build and execute queries.
	 *
	 * @param emf  the {@link EntityManagerFactory} to use
	 * @param type the return-type of the query (the underlying JPA)
	 */
	public JpqlDao(final EntityManagerFactory emf, final Class<P> type) {
		super(emf, type);
	}

	/**
	 * Build a SELECT(*)-like query. The result will be of the underlying generic
	 * type.
	 *
	 * @return a query-builder
	 */
	public SelectQueryBuilder<P, P> select() {
		return new SelectQueryBuilder<>(emf, this, type);
	}

	/**
	 * Build a SELECT(*)-like query. The result will be of the given type (use this
	 * for a COUNT(*) query, for example).
	 *
	 * @param <T>        the type the result will be
	 * @param resultType the type the result will be
	 * @return a query-builder
	 */
	public <T> SelectQueryBuilder<P, T> select(final Class<T> resultType) {
		return new SelectQueryBuilder<>(emf, this, resultType);
	}

	/**
	 * Build a SELECT-query with a custom select-clause. The result will be of the
	 * given type (use this for a COUNT(*) query, for example).
	 *
	 * @param <T>          the type the result will be
	 * @param selectClause your custom select-clause (the base-object has the alias
	 *                     'o'. So the default would be "o" internally resulting in
	 *                     a "SELECT o")
	 * @param resultType   the type the result will be
	 * @return a query-builder
	 */
	public <T> SelectQueryBuilder<P, T> select(final String selectClause, final Class<T> resultType) {
		SelectQueryBuilder<P, T> b = new SelectQueryBuilder<>(emf, this, resultType);
		b.setSelect(selectClause);
		return b;
	}

	/**
	 * Build a SELECT-query with a custom select-clause. The result will be of the
	 * underlying generic type.
	 *
	 * @param selectClause your custom select-clause (the base-object has the alias
	 *                     'o'. So the default would be "o" internally resulting in
	 *                     a "SELECT o")
	 * @return a query-builder
	 */
	public <T> SelectQueryBuilder<P, P> select(final String selectClause) {
		SelectQueryBuilder<P, P> b = new SelectQueryBuilder<>(emf, this, type);
		b.setSelect(selectClause);
		return b;
	}
}
