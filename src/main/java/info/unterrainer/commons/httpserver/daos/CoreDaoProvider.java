package info.unterrainer.commons.httpserver.daos;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface CoreDaoProvider<P extends BasicJpa, E> {

	CoreDao<P, E> getCoreDao();
}
