package info.unterrainer.commons.httpserver.daos;

import jakarta.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class ListQueryBuilder<P extends BasicJpa, T> extends BasicListQueryBuilder<P, T, ListQueryBuilder<P, T>>
		implements QueryInterface<P, T> {

	ListQueryBuilder(final EntityManagerFactory emf, final JpqlDao<P> dao, final Class<T> resultType) {
		super(emf, dao, resultType);
	}

	public ListQuery<P, T> build() {
		return new ListQuery<>(emf, this);
	}
}
