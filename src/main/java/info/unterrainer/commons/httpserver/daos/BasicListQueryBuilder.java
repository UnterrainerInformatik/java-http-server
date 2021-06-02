package info.unterrainer.commons.httpserver.daos;

import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BasicListQueryBuilder<P extends BasicJpa, X, R extends BasicListQueryBuilder<P, X, R>>
		extends BasicQueryGeneralBuilder<P, X, R> {

	protected final EntityManagerFactory emf;
	@Getter
	protected final BasicJpqlDao<P> dao;
	protected final Class<X> resultType;

	protected String selectClause = "o";
	protected String orderByClause;
	protected boolean lockPessimistic = false;

	void setSelect(final String selectClause) {
		this.selectClause = selectClause;
		if (this.selectClause == null || this.selectClause.isBlank())
			this.selectClause = "o";
	}

	public TypedQuery<X> getTypedQuery(final EntityManager em) {
		return dao.coreDao.getQuery(em, selectClause, joinClause, whereClause, parameters, resultType, orderByClause,
				lockPessimistic, null, tenantIds);
	}

	public javax.persistence.Query getCountQuery(final EntityManager em) {
		return dao.coreDao.getCountQuery(em, selectClause, joinClause, whereClause, parameters, null, tenantIds);
	}

	/**
	 * Sets the order-by-clause to the standard-order, which is {@code "o.id ASC"}
	 * and since this is the default as well, this effectively resets it to default.
	 *
	 * @return an instance of this builder to provide a fluent interface
	 */
	public R setStandardOrder() {
		return orderBy(null);
	}

	/**
	 * Sets the order-by-clause to the reversed standard-order, which is
	 * {@code "o.id DESC"}.
	 *
	 * @return an instance of this builder to provide a fluent interface
	 */
	public R setReversedStandardOrder() {
		return orderBy("o.id DESC");
	}

	/**
	 * Clears the parameters and resets them to default.
	 * <p>
	 * Default is an empty map.
	 *
	 * @return an instance of this builder to provide a fluent interface
	 */
	@Override
	public R clearParameters() {
		return parameters(null);
	}

	/**
	 * Set parameters used in all the clauses.
	 * <p>
	 * Default is an empty map.<br>
	 * To reset it to default, set it to null.
	 *
	 * @param params the new {@link ParamMap}
	 * @return an instance of this builder to provide a fluent interface
	 */
	@Override
	@SuppressWarnings("unchecked")
	public R parameters(final ParamMap params) {
		if (params == null)
			return (R) this;
		parameters = params.getParameters();
		if (this.parameters == null)
			parameters = new HashMap<>();
		return (R) this;
	}

	/**
	 * Adds a single parameter that will be used in all the clauses.
	 *
	 * @param paramKey   the key of the parameter
	 * @param paramValue the value of the parameter
	 * @return an instance of this builder to provide a fluent interface
	 */
	@Override
	@SuppressWarnings("unchecked")
	public R addParam(final String paramKey, final Object paramValue) {
		parameters.put(paramKey, paramValue);
		return (R) this;
	}

	/**
	 * Sets a custom order-by-clause.
	 * <p>
	 * Default is "o.id ASC"<br>
	 * To reset it to default, set it to null.<br>
	 * To completely delete it, set it to an empty string.
	 *
	 * @param orderByClause the new clause
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R orderBy(final String orderByClause) {
		this.orderByClause = orderByClause;
		return (R) this;
	}

	/**
	 * Adds an ASC-segment to the order-by-clause.
	 *
	 * @param field the name of the field ("o.id" or "o.createdOn" for example)
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R asc(final String field) {
		if (orderByClause == null || orderByClause.isBlank())
			orderByClause = field;
		else
			orderByClause += ", " + field;
		orderByClause += " ASC";
		return (R) this;
	}

	/**
	 * Adds an DESC-segment to the order-by-clause.
	 *
	 * @param field the name of the field ("o.id" or "o.createdOn" for example)
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R desc(final String field) {
		if (orderByClause == null || orderByClause.isBlank())
			orderByClause = field;
		else
			orderByClause += ", " + field;
		orderByClause += " DESC";
		return (R) this;
	}

	/**
	 * Sets this query to be locked pessimistically when being called, so that this
	 * entityManager is the only one that can access this
	 *
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R lockPessimistic() {
		lockPessimistic = true;
		return (R) this;
	}
}
