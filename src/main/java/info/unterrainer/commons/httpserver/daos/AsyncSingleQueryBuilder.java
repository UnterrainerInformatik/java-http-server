package info.unterrainer.commons.httpserver.daos;

import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;

public class AsyncSingleQueryBuilder<P extends BasicAsyncJpa, T>
		extends BasicSingleQueryBuilder<P, T, AsyncSingleQueryBuilder<P, T>> {

	AsyncSingleQueryBuilder(final AsyncJpqlDao<P> dao, final Long id) {
		super(dao, id);
	}

	public P get() {
		if (entityManager == null)
			return dao._getById(id);
		return dao._getById(entityManager, id);
	}
}
