package info.unterrainer.commons.httpserver.interceptors;

import java.util.function.BiConsumer;

import info.unterrainer.commons.httpserver.GenericHandlerGroupBuilder;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InterceptorParamBuilder<P extends BasicJpa, J extends BasicJson, E> {

	private final GenericHandlerGroupBuilder<P, J, E> parent;
	private final BiConsumer<InterceptorData, String> saveInterceptorData;

	private String selectClause = "o";
	private String joinClause = "";
	private String orderByClause = "";
	private String query = "";

	/**
	 * This helps you to add something to the select-clause, like 'distinct' or a
	 * limit.<br>
	 * The standard table always gets the alias 'o'.
	 *
	 * @param selectClause the clause (Example:
	 *                     {@code 'DISTINCT ' + ThisJpa.class.getSimpleName + ' AS o'})
	 * @return itself in order to provide a fluent interface.
	 */
	public InterceptorParamBuilder<P, J, E> select(final String selectClause) {
		this.selectClause = selectClause.trim().toLowerCase().startsWith("select")
				? selectClause.trim().substring(6).trim()
				: selectClause.trim();
		return this;
	}

	/**
	 * Here you can specify a special join-clause that will be added to your query
	 * later on.<br>
	 * The aliases you give here may be used in all other parts of the query.
	 *
	 * @param joinClause the clause (Example:
	 *                   {@code ' JOIN ' + MyClass.getSimpleName() + ' AS f ON o.fId=f.id'})
	 * @return itself in order to provide a fluent interface.
	 */
	public InterceptorParamBuilder<P, J, E> join(final String joinClause) {
		this.joinClause = " " + joinClause.trim();
		return this;
	}

	/**
	 * Here you can specify a special order-by-clause that will be appended to your
	 * SQL-query later on.
	 *
	 * @param orderByClause the clause (Example: {@code 'o.name DESC'}
	 * @return itself in order to provide a fluent interface.
	 */
	public InterceptorParamBuilder<P, J, E> orderBy(final String orderByClause) {
		this.orderByClause = " " + orderByClause.trim();
		return this;
	}

	/**
	 * This is where you declare your rules that will construct your query.<br>
	 * <b>Be sure to have the correct indexes placed on your tables for your queries
	 * to perform well.</b>
	 * <p>
	 * - The operators are 'AND' and 'OR' and you may use parentheses '(' and ')' to
	 * group your sub-expressions.<br>
	 * {@code Example: 'term1 AND (term2 OR term3)'}<br>
	 * - Operator precedence automatically is AND before OR.<br>
	 * - A term starts with a DB-field followed by either an operator and a
	 * JSON-field, or a null-operator.<br>
	 * - JSON-fields start with ':' and end on a bracket-expression containing the
	 * type of field.<br>
	 * - Allowed fields are:
	 * {@code string, boolean, int, long, float, double and datetime} (The system
	 * knows how to convert these to DB-values).<br>
	 * {@code Example: 'dbFieldName = :jsonName[string] AND dbId = :id[long] OR otherDbField IS NOT NULL AND anotherDbField IS NULL'}<br>
	 * - When using Enums you have to specify the type of the Enum to cast to
	 * preceded by a '~'.<br>
	 * {@code Example: 'dbEnumField = :jsonField[~MyEnumClass]'}<br>
	 * <p>
	 * - Term operators are: {@code >, <, >=, <=, = or ==, <> or != and LIKE} (which
	 * automatically adds the wildcards like %yourvalue% before going to the
	 * database).<br>
	 * - Every term may be optional when you precede the DB-field-name with a
	 * '?'.<br>
	 * {@code Example: '?dbFieldName = :jsonName[string] AND dbId = :id[long]' requested with an empty 'jsonName' will result in 'dbId = :id[long]'}
	 * Everything is case-insensitive except for the parameters of course.
	 *
	 * @param query a string that will be parsed constructing your where-clause and
	 *              all parameter-assignments
	 * @return itself in order to provide a fluent interface.
	 */
	public InterceptorParamBuilder<P, J, E> query(final String query) {
		this.query = query;
		return this;
	}

	/**
	 * Builds this Interceptor and returns you to the fluent interface of the
	 * underlying {@link GenericHandlerGroupBuilder}.
	 *
	 * @return the {@link GenericHandlerGroupBuilder} you came from.
	 */
	public GenericHandlerGroupBuilder<P, J, E> build() {
		InterceptorData r = InterceptorData.builder()
				.selectClause(selectClause)
				.joinClause(joinClause)
				.orderByClause(orderByClause)
				.build();
		saveInterceptorData.accept(r, query);
		return parent;
	}
}
