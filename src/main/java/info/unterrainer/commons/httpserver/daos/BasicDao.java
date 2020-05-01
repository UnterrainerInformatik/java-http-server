package info.unterrainer.commons.httpserver.daos;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface BasicDao<P extends BasicJpa> {

	P getById(Long id);

	ListJson<P> getList(int offset, int size);

	P create(P entity);

	P persist(P entity);

	void delete(Long id);
}
