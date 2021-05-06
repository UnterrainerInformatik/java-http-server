package info.unterrainer.commons.httpserver.daos;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface BasicDao<P extends BasicJpa, E> {

	P getById(Long id);

	P create(P entity);

	P update(P entity);

	ListJson<P> getList(E em, long offset, long size, String selectClause, String joinClause, String whereClause,
			ParamMap params, String orderByClause);

	UpsertResult<P> upsert(String whereClause, ParamMap params, P entity);

	UpsertResult<P> upsert(Query<P, P> query, P entity);

	void delete(Long id);

	P getById(E em, Long id);

	<T> QueryBuilder<P, T> query(Class<T> resultType);

	QueryBuilder<P, P> query();

	CountQueryBuilder<P, P> countQuery();

	P create(E em, P entity);

	P update(E em, P entity);

	UpsertResult<P> upsert(E em, String whereClause, ParamMap params, P entity);

	UpsertResult<P> upsert(E em, Query<P, P> query, P entity);

	void delete(E em, Long id);
}
