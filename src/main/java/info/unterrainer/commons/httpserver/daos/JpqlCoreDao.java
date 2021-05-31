package info.unterrainer.commons.httpserver.daos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.jreutils.DateUtils;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.rdbutils.enums.AsyncState;
import lombok.Getter;

public class JpqlCoreDao<P extends BasicJpa> implements CoreDao<P, EntityManager> {

	protected final EntityManagerFactory emf;
	protected final Class<P> type;
	@Getter
	protected JpqlTransactionManager transactionManager;

	public JpqlCoreDao(final EntityManagerFactory emf, final Class<P> type) {
		super();
		this.emf = emf;
		this.type = type;
		transactionManager = new JpqlTransactionManager(emf);
	}

	@Override
	public P create(final EntityManager em, final P entity) {
		LocalDateTime time = DateUtils.nowUtc();
		entity.setCreatedOn(time);
		entity.setEditedOn(time);
		em.persist(entity);
		return entity;
	}

	@Override
	public void delete(final EntityManager em, final Long id) {
		em.createQuery(String.format("DELETE FROM %s AS o WHERE o.id = :id", type.getSimpleName()))
				.setParameter("id", id)
				.executeUpdate();
	}

	@Override
	public P getById(final EntityManager em, final Long id) {
		try {
			return getQuery(em, "o", null, "o.id = :id", Map.of("id", id), type, null, false, null).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public ListJson<P> getList(final EntityManager em, final Long offset, final Long size, final String selectClause,
			final String joinClause, final String whereClause, final ParamMap params, final String orderByClause) {
		ListJson<P> r = new ListJson<>();
		r.setEntries(
				getList(em,
						getQuery(em, selectClause, joinClause, whereClause,
								params == null ? null : params.getParameters(), type, orderByClause, false, null),
						offset, size));
		r.setCount((Long) getCountQuery(em, selectClause, joinClause, whereClause,
				params == null ? null : params.getParameters(), null).getSingleResult());
		return r;
	}

	@Override
	public P update(final EntityManager em, final P entity) {
		LocalDateTime time = DateUtils.nowUtc();
		entity.setEditedOn(time);
		return em.merge(entity);
	}

	<T> TypedQuery<T> getQuery(final EntityManager em, final String selectClause, final String joinClause,
			final String whereClause, final Map<String, Object> params, final Class<T> type, final String orderBy,
			final boolean lockPessimistic, final Set<AsyncState> asyncStates) {
		String query = "SELECT ";
		if (selectClause == null || selectClause.isBlank())
			query += "o";
		else
			query += selectClause;
		query += " FROM %s AS o";

		if (joinClause != null && !joinClause.isBlank())
			query += " " + joinClause;

		query += buildWhereClause(whereClause, asyncStates);

		if (orderBy == null)
			query += " ORDER BY o.id ASC";
		else if (!orderBy.isBlank())
			query += " ORDER BY " + orderBy;

		query = String.format(query, this.type.getSimpleName());

		@SuppressWarnings("unchecked")
		Class<T> t = (Class<T>) this.type;
		if (type != null)
			t = type;

		TypedQuery<T> q = em.createQuery(query, t);
		if (lockPessimistic)
			q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		q = addAsyncStatesParamsToQuery(asyncStates, q);
		if (params != null)
			for (Entry<String, Object> e : params.entrySet())
				q.setParameter(e.getKey(), e.getValue());
		return q;
	}

	<T> TypedQuery<T> getDeleteQuery(final EntityManager em, final String joinClause, final String whereClause,
			final Map<String, Object> params) {
		String query = "DELETE FROM  %s AS o";

		if (joinClause != null && !joinClause.isBlank())
			query += " " + joinClause;

		query += buildWhereClause(whereClause, null);

		query = String.format(query, this.type.getSimpleName());

		@SuppressWarnings("unchecked")
		Class<T> t = (Class<T>) this.type;

		TypedQuery<T> q = em.createQuery(query, t);
		if (params != null)
			for (Entry<String, Object> e : params.entrySet())
				q.setParameter(e.getKey(), e.getValue());
		return q;
	}

	Query getCountQuery(final EntityManager em, final String selectClause, final String joinClause,
			final String whereClause, final Map<String, Object> params, final Set<AsyncState> asyncStates) {
		String query = "SELECT COUNT(";
		if (selectClause == null || selectClause.isBlank())
			query += "o.id";
		else
			query += selectClause;
		query += ") FROM %s AS o";
		if (joinClause != null && !joinClause.isBlank())
			query += " " + joinClause;
		query += buildWhereClause(whereClause, asyncStates);

		Query q = em.createQuery(String.format(query, this.type.getSimpleName()));

		q = addAsyncStatesParamsToQuery(asyncStates, q);
		if (params != null)
			for (Entry<String, Object> e : params.entrySet())
				q.setParameter(e.getKey(), e.getValue());
		return q;
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

	private boolean isAllowed(final EntityManager em, final TenantData tenantData) {
		TypedQuery<Long> query = getQuery(em, "o.id",
				"RIGHT JOIN " + tenantData.getJpa().getClass().getSimpleName() + " tenantTable on o.id = tenantTable."
						+ tenantData.getReferenceField(),
				"tenantTable." + tenantData.getReferenceField() + " IS NULL OR tenantTable." + tenantData.getIdField()
						+ " = :tenantId",
				null, Long.class, null, false, null);
		query.setMaxResults(1);
		List<Long> list = query.getResultList();
		return list != null && list.size() > 0;
	}

	private boolean isSet(final String str) {
		return str != null && !str.isBlank();
	}

	private boolean isSet(final Set<?> set) {
		return set != null && !set.isEmpty();
	}

	private String buildWhereClause(final String whereClause, final Set<AsyncState> asyncStates) {
		String r = "";
		if (!isSet(whereClause) && !isSet(asyncStates))
			return r;

		r = " WHERE ";
		if (isSet(whereClause) && !isSet(asyncStates))
			r += whereClause;

		if (!isSet(whereClause) && isSet(asyncStates))
			r += addAsyncStatesToWhereClause(asyncStates);

		if (isSet(whereClause) && isSet(asyncStates))
			r += "( " + whereClause + " ) AND ( " + addAsyncStatesToWhereClause(asyncStates) + " )";

		return r;
	}

	private String addAsyncStatesToWhereClause(final Set<AsyncState> asyncStates) {
		StringBuilder sb = new StringBuilder();
		boolean isFirst = true;

		for (int i = 0; i < asyncStates.size(); i++) {
			if (isFirst)
				isFirst = false;
			else
				sb.append(" OR ");
			sb.append("state = :state");
			sb.append(i);
		}

		return sb.toString();
	}

	private <T> TypedQuery<T> addAsyncStatesParamsToQuery(final Set<AsyncState> asyncStates,
			final TypedQuery<T> query) {

		if (!isSet(asyncStates))
			return query;

		int count = 0;
		for (AsyncState state : asyncStates)
			query.setParameter("state" + count++, state);

		return query;
	}

	private Query addAsyncStatesParamsToQuery(final Set<AsyncState> asyncStates, final Query query) {

		if (!isSet(asyncStates))
			return query;

		int count = 0;
		for (AsyncState state : asyncStates)
			query.setParameter("state" + count++, state);

		return query;
	}
}
