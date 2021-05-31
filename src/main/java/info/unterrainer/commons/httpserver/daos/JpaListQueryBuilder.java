package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class JpaListQueryBuilder<P extends BasicJpa> extends BasicListQueryBuilder<P, P, JpaListQueryBuilder<P>>
		implements QueryInterface<P, P> {

	JpaListQueryBuilder(final EntityManagerFactory emf, final JpqlDao<P> dao, final Class<P> resultType) {
		super(emf, dao, resultType);
	}

	public JpaListQuery<P> build() {
		return new JpaListQuery<>(emf, this);
	}
}
