package info.unterrainer.commons.httpserver.interceptors;

import java.util.function.Consumer;

import info.unterrainer.commons.httpserver.GenericHandlerGroupBuilder;
import info.unterrainer.commons.httpserver.daos.ParamMap;
import info.unterrainer.commons.httpserver.rql.RqlData;
import info.unterrainer.commons.httpserver.rql.RqlUtils;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InterceptorParamBuilder<P extends BasicJpa, J extends BasicJson, E> {

	private final GenericHandlerGroupBuilder<P, J, E> parent;
	private final RqlUtils rqlUtils;
	private final Consumer<InterceptorData> saveInterceptorData;

	private String selectClause = "o";
	private String joinClause = "";
	private String query = "";

	public InterceptorParamBuilder<P, J, E> select(final String selectClause) {
		this.selectClause = selectClause;
		return this;
	}

	public InterceptorParamBuilder<P, J, E> join(final String joinClause) {
		this.joinClause = joinClause;
		return this;
	}

	public InterceptorParamBuilder<P, J, E> query(final String query) {
		this.query = query;
		return this;
	}

	public GenericHandlerGroupBuilder<P, J, E> build() {
		RqlData data = rqlUtils.parseRql(query);
		InterceptorData r = InterceptorData.builder()
				.selectClause(selectClause)
				.whereClause(data.getParsedCommandAsString())
				.joinClause(joinClause)
				.params(ParamMap.builder().parameters(data.getParams()).build())
				.partOfQueryString(data.getQueryStringAsString())
				.build();
		saveInterceptorData.accept(r);
		return parent;
	}
}
