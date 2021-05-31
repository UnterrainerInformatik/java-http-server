package info.unterrainer.commons.httpserver.daos;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface CoreDao<P extends BasicJpa, E> {

	DaoTransactionManager<E> getTransactionManager();

	P create(E manager, P mappedJpa);

	P getById(E manager, Long id);

	ListJson<P> getList(E manager, Long offset, Long size, String selectClause, String joinClause, String whereClause,
			ParamMap params, String orderByClause);

	void delete(E manager, Long id);

	P update(E manager, P mappedJpa);
}
