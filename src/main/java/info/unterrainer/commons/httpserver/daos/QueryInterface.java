package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

interface QueryInterface<P extends BasicJpa, T> {

	EntityManager getEntityManager();

	BasicJpqlDao<P> getDao();

	SelectQuery<P, T> build();

	TypedQuery<T> getTypedQuery(final EntityManager em);

	javax.persistence.Query getCountQuery(final EntityManager em);
}
