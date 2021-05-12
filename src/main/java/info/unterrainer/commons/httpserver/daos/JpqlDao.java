package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class JpqlDao<P extends BasicJpa> extends BasicJpqlDao<P> implements BasicDao<P, EntityManager> {

	public JpqlDao(final EntityManagerFactory emf, final Class<P> type) {
		super(emf, type);
	}

	public QueryBuilder<P, P> query() {
		return new QueryBuilder<>(emf, this, type);
	}

	public <T> QueryBuilder<P, T> query(final Class<T> resultType) {
		return new QueryBuilder<>(emf, this, resultType);
	}
}
