package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface BasicDao<P extends BasicJpa> {

	P getById(Long id);

	ListJson<P> getList(long offset, long size);

	P create(P entity);

	P persist(P entity);

	void delete(Long id);

	P getById(EntityManager em, Long id);

	ListJson<P> getList(EntityManager em, long offset, long size);

	P create(EntityManager em, P entity);

	P persist(EntityManager em, P entity);

	void delete(EntityManager em, Long id);
}
