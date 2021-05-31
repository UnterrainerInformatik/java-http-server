package info.unterrainer.commons.httpserver.daos;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.jreutils.DateUtils;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
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

	P update(final EntityManager em, final P entity) {
		LocalDateTime time = DateUtils.nowUtc();
		entity.setEditedOn(time);
		return em.merge(entity);
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

	UpsertResult<P> upsert(final EntityManager em, final TypedQuery<P> query, final P entity) {
		boolean wasInserted = false;
		boolean wasUpdated = false;
		P e = getFirst(em, query);
		if (e == null) {
			e = coreDao.create(em, entity);
			wasInserted = true;
		} else {
			entity.setId(e.getId());
			entity.setCreatedOn(e.getCreatedOn());
			e = update(em, entity);
			wasUpdated = true;
		}
		return UpsertResult.<P>builder().wasInserted(wasInserted).wasUpdated(wasUpdated).jpa(e).build();
	}
}
