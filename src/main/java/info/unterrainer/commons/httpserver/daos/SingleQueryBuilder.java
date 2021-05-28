package info.unterrainer.commons.httpserver.daos;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class SingleQueryBuilder<P extends BasicJpa, T> extends BasicSingleQueryBuilder<P, T, SingleQueryBuilder<P, T>> {

	SingleQueryBuilder(final JpqlDao<P> dao, final Long id) {
		super(dao, id);
	}

	public P get() {
		if (entityManager == null)
			return dao._getById(id);
		return dao._getById(entityManager, id);
	}
}
