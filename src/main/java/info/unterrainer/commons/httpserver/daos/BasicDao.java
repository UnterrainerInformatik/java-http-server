package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface BasicDao<P extends BasicJpa> {

	P getById(Long id);

	ListJson<P> getList(long offset, long size);

	ListJson<P> getList(long offset, long size, String whereClause, ParamMap params);

	P create(P entity);

	P update(P entity);

	UpsertResult<P> upsert(String whereClause, ParamMap params, P entity);

	UpsertResult<P> upsert(TypedQuery<P> query, P entity);

	void delete(Long id);

	P getById(EntityManager em, Long id);

	ListJson<P> getList(EntityManager em, long offset, long size);

	ListJson<P> getList(EntityManager em, long offset, long size, String whereClause, ParamMap params);

	P create(EntityManager em, P entity);

	P update(EntityManager em, P entity);

	UpsertResult<P> upsert(EntityManager em, String whereClause, ParamMap params, P entity);

	UpsertResult<P> upsert(EntityManager em, TypedQuery<P> query, P entity);

	void delete(EntityManager em, Long id);
}
