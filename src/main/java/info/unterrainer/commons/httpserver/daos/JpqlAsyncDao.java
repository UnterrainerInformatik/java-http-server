package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;

public class JpqlAsyncDao<P extends BasicAsyncJpa> extends BasicJpqlDao<P> {

	public JpqlAsyncDao(final EntityManagerFactory emf, final Class<P> type) {
		super(emf, type);
	}

	public QueryAsyncBuilder<P, P> query() {
		return new QueryAsyncBuilder<>(emf, this, type);
	}

	public <T> QueryAsyncBuilder<P, T> query(final Class<T> resultType) {
		return new QueryAsyncBuilder<>(emf, this, resultType);
	}
}
