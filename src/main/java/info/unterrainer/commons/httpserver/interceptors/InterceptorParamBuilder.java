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

	public InterceptorParamBuilder<P, J, E> select(final String selectClause) {
		this.selectClause = selectClause;
		return this;
	}

	public InterceptorParamBuilder<P, J, E> join(final String joinClause) {
		this.joinClause = joinClause.startsWith(" ") ? joinClause : " " + joinClause;
		return this;
	}

	public InterceptorParamBuilder<P, J, E> orderBy(final String orderByClause) {
		this.orderByClause = orderByClause.startsWith(" ") ? orderByClause : " " + orderByClause;
		return this;
	}

	public InterceptorParamBuilder<P, J, E> query(final String query) {
		this.query = query;
		return this;
	}

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
