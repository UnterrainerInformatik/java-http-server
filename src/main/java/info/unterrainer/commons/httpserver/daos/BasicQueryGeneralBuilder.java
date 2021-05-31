package info.unterrainer.commons.httpserver.daos;

import java.util.HashMap;
import java.util.Map;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BasicQueryGeneralBuilder<P extends BasicJpa, T, R extends BasicQueryGeneralBuilder<P, T, R>>
		extends BasicQueryEntityManagerBuilder<P, T, R> {

	protected String joinClause;
	protected String whereClause;

	protected Map<String, Object> parameters = new HashMap<>();

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
}
