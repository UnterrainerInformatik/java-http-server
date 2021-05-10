package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;

public class JpqlAsyncDao<P extends BasicAsyncJpa> extends JpqlDao<P> {

	public JpqlAsyncDao(final EntityManagerFactory emf, final Class<P> type) {
		super(emf, type);
	}

	@Override
	public QueryAsyncBuilder<P, P> query() {
		return new QueryAsyncBuilder<>(emf, this, type);
	}
}
