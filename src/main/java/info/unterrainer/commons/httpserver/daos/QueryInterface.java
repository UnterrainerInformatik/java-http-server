package info.unterrainer.commons.httpserver.daos;

import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

interface QueryInterface<P extends BasicJpa, T> {

	EntityManager getEntityManager();

	BasicJpqlDao<P> getDao();

	TypedQuery<T> getTypedQuery(final EntityManager em);

	jakarta.persistence.Query getCountQuery(final EntityManager em);

	Set<Long> getReadTenantIds();

	Set<Long> getWriteTenantIds();
}
