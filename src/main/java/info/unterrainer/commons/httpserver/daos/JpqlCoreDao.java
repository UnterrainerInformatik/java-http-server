package info.unterrainer.commons.httpserver.daos;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.exceptions.ForbiddenException;
import info.unterrainer.commons.httpserver.exceptions.InternalServerErrorException;
import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.jreutils.DateUtils;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.rdbutils.enums.AsyncState;
import io.javalin.http.Context;
import lombok.Getter;

public class JpqlCoreDao<P extends BasicJpa> implements CoreDao<P, EntityManager> {

	protected final Class<P> type;
	@Getter
	protected JpqlTransactionManager transactionManager;
	@Getter
	protected TenantData tenantData;

	public JpqlCoreDao(final EntityManagerFactory emf, final Class<P> type) {
		super();
		this.type = type;
		transactionManager = new JpqlTransactionManager(emf, null);
	}

	public JpqlCoreDao(final Function<Context, EntityManagerFactory> entityManagerFactorySupplier,
			final Class<P> type) {
		super();
		this.type = type;
		transactionManager = new JpqlTransactionManager(null, entityManagerFactorySupplier);
	}

	@Override
	public P create(final EntityManager em, final P entity, final Set<Long> tenantIds) {
		LocalDateTime time = DateUtils.nowUtc();
		entity.setCreatedOn(time);
		entity.setEditedOn(time);
		em.persist(entity);

		if (hasTenantData())
			try {
				for (Long tenantId : tenantIds) {
					BasicJpa tenantJpa = tenantData.getType().getConstructor().newInstance();
					tenantData.getReferenceSetMethod().invoke(tenantJpa, entity.getId());
					tenantData.getTenantIdSetMethod().invoke(tenantJpa, tenantId);

					tenantJpa.setCreatedOn(time);
					tenantJpa.setEditedOn(time);
					em.persist(tenantJpa);
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new InternalServerErrorException(
						String.format("Error creating permission-entry in [%s]", tenantData.getType().getSimpleName()));
			}
		return entity;
	}

	@Override
	public void delete(final EntityManager em, final Long id, final Set<Long> tenantIds) {
		if (!isAllowed(em, id, tenantIds))
			return;

		em.createQuery(String.format("DELETE FROM %s AS o WHERE o.id = :id", type.getSimpleName()))
				.setParameter("id", id)
				.executeUpdate();
	}

	@Override
	public P getById(final EntityManager em, final Long id, final Set<Long> tenantIds) {
		try {
			return getQuery(em, "o", null, "o.id = :id", Map.of("id", id), type, null, false, null, tenantIds)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public ListJson<P> getList(final EntityManager em, final Long offset, final Long size, final String selectClause,
			final String joinClause, final String whereClause, final ParamMap params, final String orderByClause,
			final Set<Long> tenantIds) {
		ListJson<P> r = new ListJson<>();
		r.setEntries(getList(em, getQuery(em, selectClause, joinClause, whereClause,
				params == null ? null : params.getParameters(), type, orderByClause, false, null, tenantIds), offset,
				size));
		r.setCount((Long) getCountQuery(em, selectClause, joinClause, whereClause,
				params == null ? null : params.getParameters(), null, tenantIds).getSingleResult());
		return r;
	}

	@Override
	public P update(final EntityManager em, final P entity, final Set<Long> tenantIds) {
		if (!isAllowed(em, entity.getId(), tenantIds))
			throw new ForbiddenException();
		LocalDateTime time = DateUtils.nowUtc();
		entity.setEditedOn(time);
		return em.merge(entity);
	}

	<T> TypedQuery<T> getQuery(final EntityManager em, final String selectClause, String joinClause, String whereClause,
			Map<String, Object> params, final Class<T> type, final String orderBy, final boolean lockPessimistic,
			final Set<AsyncState> asyncStates, final Set<Long> tenantIds) {
		String query = "SELECT ";
		if (selectClause == null || selectClause.isBlank())
			query += "o";
		else
			query += selectClause;
		query += " FROM %s AS o";

		joinClause = addTenantJoin(joinClause);
		if (joinClause != null && !joinClause.isBlank())
			query += " " + joinClause;

		whereClause = addTenantWhere(whereClause, tenantIds);
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

		params = addTenantParams(params, tenantIds);
		if (params != null)
			for (Entry<String, Object> e : params.entrySet())
				q.setParameter(e.getKey(), e.getValue());
		return q;
	}

	Query getCountQuery(final EntityManager em, final String selectClause, String joinClause, String whereClause,
			Map<String, Object> params, final Set<AsyncState> asyncStates, final Set<Long> tenantIds) {
		String query = "SELECT COUNT(";
		if (selectClause == null || selectClause.isBlank())
			query += "o.id";
		else
			query += selectClause;
		query += ") FROM %s AS o";

		joinClause = addTenantJoin(joinClause);
		if (joinClause != null && !joinClause.isBlank())
			query += " " + joinClause;

		whereClause = addTenantWhere(whereClause, tenantIds);
		query += buildWhereClause(whereClause, asyncStates);
		params = addTenantParams(params, tenantIds);

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

	private boolean isAllowed(final EntityManager em, final Long id, final Set<Long> tenantIds) {
		if (!hasTenantData())
			return true;
		if (tenantIds == null)
			return false;
		if (tenantIds.contains(null))
			return true;
		TypedQuery<Long> query = getQuery(em, "o.id", null, "o.id = :id", Map.of("id", id), Long.class, null, false,
				null, tenantIds);
		query.setMaxResults(1);
		List<Long> list = query.getResultList();
		return list != null && list.size() > 0;
	}

	private String addTenantJoin(final String joinClause) {
		if (!hasTenantData())
			return joinClause;

		String r = joinClause;
		if (joinClause == null || joinClause.isBlank())
			r = "";

		r += String.format(" LEFT JOIN %s tenantTable on o.id = tenantTable.%s", tenantData.getType().getSimpleName(),
				tenantData.getMainTableIdReferenceField());
		return r;
	}

	private String addTenantWhere(final String whereClause, final Set<Long> tenantIds) {
		if (!hasTenantData())
			return whereClause;

		String r = "";
		if (whereClause != null && !whereClause.isBlank())
			r = "( " + whereClause + ") AND ";

		r += String.format("( tenantTable.%1$s IS NULL ", tenantData.getTenantIdField());
		if (tenantIds != null && !tenantIds.isEmpty())
			r += String.format(" OR tenantTable.%1$s IN (:tenantIds) ", tenantData.getTenantIdField());
		r += ")";
		return r;
	}

	private Map<String, Object> addTenantParams(final Map<String, Object> map, final Set<Long> tenantIds) {
		Map<String, Object> m;
		if (map == null)
			m = new HashMap<>();
		else
			m = new HashMap<>(map);
		if (!hasTenantData() || tenantIds == null || tenantIds.isEmpty())
			return m;
		m.put("tenantIds", tenantIds);
		return m;
	}

	private boolean hasTenantData() {
		return tenantData != null && tenantData.getType() != null && tenantData.getMainTableIdReferenceField() != null
				&& tenantData.getTenantIdField() != null;
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
