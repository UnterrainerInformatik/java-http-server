package info.unterrainer.commons.httpserver.daos;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SingleQueryBuilder<P extends BasicJpa, T>
		extends BasicQueryEntityManagerBuilder<P, T, SingleQueryBuilder<P, T>> {

	@Getter
	protected final BasicJpqlDao<P> dao;
	protected final Long id;

	public P get() {
		if (entityManager == null)
			return dao._getById(id);
		return dao._getById(entityManager, id);
	}

	public void delete() {
		if (entityManager == null) {
			dao._delete(id);
			return;
		}
		dao._delete(entityManager, id);
	}
}
