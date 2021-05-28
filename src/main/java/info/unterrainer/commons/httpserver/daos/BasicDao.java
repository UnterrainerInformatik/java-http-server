package info.unterrainer.commons.httpserver.daos;

import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface BasicDao<P extends BasicJpa, E> {

	P _getById(Long id);

	P create(P entity);

	P update(P entity);

	ListJson<P> getList(E em, long offset, long size, String selectClause, String joinClause, String whereClause,
			ParamMap params, String orderByClause);

	UpsertResult<P> upsert(String whereClause, ParamMap params, P entity);

	UpsertResult<P> upsert(TypedQuery<P> query, P entity);

	void _delete(Long id);

	P _getById(E em, Long id);

	P create(E em, P entity);

	P update(E em, P entity);

	UpsertResult<P> upsert(E em, String whereClause, ParamMap params, P entity);

	UpsertResult<P> upsert(E em, TypedQuery<P> query, P entity);

	void _delete(E em, Long id);
}
