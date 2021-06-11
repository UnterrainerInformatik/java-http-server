package info.unterrainer.commons.httpserver.daos;

import java.util.List;
import java.util.Set;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import io.javalin.http.Context;
import lombok.Getter;

public class BasicJpqlDao<P extends BasicJpa> implements CoreDaoProvider<P, EntityManager> {

	protected final EntityManagerFactory emf;
	protected final Class<P> type;
	@Getter
	protected JpqlCoreDao<P> coreDao;

	public BasicJpqlDao(final EntityManagerFactory emf, final Class<P> type) {
		super();
		this.emf = emf;
		this.type = type;
		coreDao = new JpqlCoreDao<>(emf, type);
	}

	public BasicJpqlDao(final Function<Context, EntityManagerFactory> entityManagerFactorySupplier,
			final Class<P> type) {
		super();
		this.emf = null;
		this.type = type;
		coreDao = new JpqlCoreDao<>(entityManagerFactorySupplier, type);
	}

	<T> List<T> getList(final EntityManager em, final TypedQuery<T> query, final long offset, final long size) {
		int s = Integer.MAX_VALUE;
		if (size < s)
			s = (int) size;
		int o = Integer.MAX_VALUE;
		if (offset < o)
			o = (int) offset;
		query.setFirstResult(o);
		query.setMaxResults(s);
		return query.getResultList();
	}

	private <T> T getFirst(final EntityManager em, final TypedQuery<T> query) {
		List<T> r = getList(em, query, 0, 1);
		if (r.size() == 1) {
			T jpa = r.get(0);
			return jpa;
		}
		return null;
	}

	UpsertResult<P> upsert(final EntityManager em, final TypedQuery<P> query, final P entity,
			final Set<Long> readTenantIds, final Set<Long> writeTenantIds) {
		boolean wasInserted = false;
		boolean wasUpdated = false;
		P e = getFirst(em, query);
		if (e == null) {
			e = coreDao.create(em, entity, writeTenantIds);
			wasInserted = true;
		} else {
			entity.setId(e.getId());
			entity.setCreatedOn(e.getCreatedOn());
			e = coreDao.update(em, entity, readTenantIds);
			wasUpdated = true;
		}
		return UpsertResult.<P>builder().wasInserted(wasInserted).wasUpdated(wasUpdated).jpa(e).build();
	}
}
