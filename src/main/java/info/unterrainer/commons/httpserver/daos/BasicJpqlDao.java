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
import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.rdbutils.enums.AsyncState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class BasicJpqlDao<P extends BasicJpa> implements BasicDao<P, EntityManager> {

	protected final EntityManagerFactory emf;
	protected final Class<P> type;

	@Override
	public P getById(final Long id) {
		return Transactions.withNewTransactionReturning(emf, em -> getById(em, id));
	}

	@Override
	public ListJson<P> getList(final EntityManager em, final long offset, final long size, final String selectClause,
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
	public P create(final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> create(em, entity));
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
	public P update(final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> update(em, entity));
	}

	@Override
	public P update(final EntityManager em, final P entity) {
		LocalDateTime time = DateUtils.nowUtc();
		entity.setEditedOn(time);
		return em.merge(entity);
	}

	public <T> List<T> getList(final EntityManager em, final TypedQuery<T> query, final long offset, final long size) {
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

	@Override
	public UpsertResult<P> upsert(final String whereClause, final ParamMap params, final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> upsert(em, getQuery(em, "o", null, whereClause,
				params == null ? null : params.getParameters(), type, null, false, null), entity));
	}

	@Override
	public UpsertResult<P> upsert(final EntityManager em, final String whereClause, final ParamMap params,
			final P entity) {
		return upsert(em, getQuery(em, "o", null, whereClause, params == null ? null : params.getParameters(), type,
				null, false, null), entity);
	}

	@Override
	public UpsertResult<P> upsert(final TypedQuery<P> query, final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> upsert(em, query, entity));
	}

	private P getFirst(final EntityManager em, final TypedQuery<P> query) {
		List<P> r = getList(em, query, 0, 1);
		if (r.size() == 1) {
			P jpa = r.get(0);
			return jpa;
		}
		return null;
	}

	@Override
	public UpsertResult<P> upsert(final EntityManager em, final TypedQuery<P> query, final P entity) {
		boolean wasInserted = false;
		boolean wasUpdated = false;
		P e = getFirst(em, query);
		if (e == null) {
			e = create(em, entity);
			wasInserted = true;
		} else {
			entity.setId(e.getId());
			entity.setCreatedOn(e.getCreatedOn());
			e = update(em, entity);
			wasUpdated = true;
		}
		return UpsertResult.<P>builder().wasInserted(wasInserted).wasUpdated(wasUpdated).jpa(e).build();
	}

	@Override
	public void delete(final Long id) {
		Transactions.withNewTransaction(emf, em -> {
			delete(em, id);
		});
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

		String msg = query;
		if (params != null)
			for (Entry<String, Object> p : params.entrySet())
				msg += "\\n  " + p.getKey() + ": " + p.getValue();
		log.debug(msg);

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

	private boolean isAllowed(final info.unterrainer.commons.httpserver.daos.QueryBuilder query,
			final EntityManager em) {
		String tenantReferenceField = "testId";
		String tenantIdField = "tenantId";
		Long tenantId = 55L;
		BasicJpa tenantJpa = null;
		getQuery(em, "o",
				"RIGHT JOIN " + tenantJpa.getClass().getSimpleName() + " tenantTable on o.id = tenantTable."
						+ tenantReferenceField,
				"tenantTable." + tenantReferenceField + " IS NULL OR tenantTable." + tenantIdField + " = :tenantId",
				null, type, null, false, null);
		return true;
	}

	Query getCountQuery(final EntityManager em, final String selectClause, final String joinClause,
			final String whereClause, final Map<String, Object> params, final Set<AsyncState> asyncStates) {
		String query = "SELECT COUNT(" + selectClause + ") FROM %s AS o";
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
}
