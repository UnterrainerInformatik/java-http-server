package info.unterrainer.commons.httpserver.daos;

import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface BasicDao<P extends BasicJpa, E> {

	P getById(Long id);

	ListJson<P> getList(long offset, long size);

	ListJson<P> getList(long offset, long size, String whereClause, ParamMap params);

	P create(P entity);

	P update(P entity);

	UpsertResult<P> upsert(String whereClause, ParamMap params, P entity);

	UpsertResult<P> upsert(TypedQuery<P> query, P entity);

	void delete(Long id);

	P getById(E em, Long id);

	ListJson<P> getList(E em, long offset, long size);

	ListJson<P> getList(E em, long offset, long size, String whereClause, ParamMap params);

	ListJson<P> getList(E em, long offset, long size, String joinClause, String whereClause, ParamMap params);

	ListJson<P> getList(E em, long offset, long size, String selectClause, String joinClause, String whereClause,
			ParamMap params);

	P create(E em, P entity);

	P update(E em, P entity);

	UpsertResult<P> upsert(E em, String whereClause, ParamMap params, P entity);

	UpsertResult<P> upsert(E em, TypedQuery<P> query, P entity);

	void delete(E em, Long id);
}
