package info.unterrainer.commons.httpserver.daos;

import java.util.Set;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public interface CoreDao<P extends BasicJpa, E> {

	TenantData getTenantData();

	DaoTransactionManager<E> getTransactionManager();

	P create(E manager, P mappedJpa, final Set<Long> tenantIds);

	P getById(E manager, Long id, final Set<Long> tenantIds);

	ListJson<P> getList(E manager, Long offset, Long size, String selectClause, String joinClause, String whereClause,
			ParamMap params, String orderByClause, final Set<Long> tenantIds);

	void delete(E manager, Long id, final Set<Long> tenantIds);

	P update(E manager, P mappedJpa, final Set<Long> tenantIds);
}
