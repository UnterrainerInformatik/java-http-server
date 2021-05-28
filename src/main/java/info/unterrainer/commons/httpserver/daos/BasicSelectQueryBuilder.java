package info.unterrainer.commons.httpserver.daos;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BasicSelectQueryBuilder<P extends BasicJpa, T, R extends BasicSelectQueryBuilder<P, T, R>> {

	protected final EntityManagerFactory emf;
	protected final BasicJpqlDao<P> dao;
	protected final Class<T> resultType;

	protected EntityManager entityManager;
	protected String selectClause = "o";
	protected String joinClause;
	protected String whereClause;
	protected String orderByClause;
	protected boolean lockPessimistic = false;

	protected Map<String, Object> parameters = new HashMap<>();

	/**
	 * Sets a custom {@link EntityManager}.
	 * <p>
	 * Default is to create one when creating the query.<br>
	 * To reset it to default, set it to null.
	 *
	 * @param em an {@link EntityManager}
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R entityManager(final EntityManager em) {
		entityManager = em;
		return (R) this;
	}

	void setSelect(final String selectClause) {
		this.selectClause = selectClause;
		if (this.selectClause == null || this.selectClause.isBlank())
			this.selectClause = "o";
	}

	/**
	 * Sets a custom join-clause.
	 * <p>
	 * Default is ""<br>
	 * To reset it to default, set it to null or directly to an empty string.
	 *
	 * @param joinClause the new clause
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R join(final String joinClause) {
		this.joinClause = joinClause;
		if (this.joinClause == null)
			this.joinClause = "";
		return (R) this;
	}

	/**
	 * Sets a custom where-clause.
	 * <p>
	 * Default is ""<br>
	 * To reset it to default, set it to null or directly to an empty string.
	 *
	 * @param whereClause the new clause
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R where(final String whereClause) {
		this.whereClause = whereClause;
		if (this.whereClause == null)
			this.whereClause = "";
		return (R) this;
	}

	/**
	 * Adds an 'AND' part to the where-clause.
	 * <p>
	 * For example: .and("o.loggedIn = :loggedIn");
	 *
	 * @param andWhereClause the clause to add
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R and(final String andWhereClause) {
		if (whereClause == null || whereClause.isBlank())
			whereClause = andWhereClause;
		else
			whereClause += " AND " + andWhereClause;
		return (R) this;
	}

	/**
	 * Adds an 'OR' part to the where-clause.
	 * <p>
	 * For example: .or("o.loggedIn = :loggedIn");
	 *
	 * @param orWhereClause the clause to add
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R or(final String orWhereClause) {
		if (whereClause == null || whereClause.isBlank())
			whereClause = orWhereClause;
		else
			whereClause += " OR " + orWhereClause;
		return (R) this;
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
