package info.unterrainer.commons.httpserver.daos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JpqlDao<P extends BasicJpa> implements BasicDao<P, EntityManager> {

	protected final EntityManagerFactory emf;
	protected final Class<P> type;

	@Override
	public P getById(final Long id) {
		return Transactions.withNewTransactionReturning(emf, em -> getById(em, id));
	}

	@Override
	public ListJson<P> getList(final long offset, final long size) {
		return Transactions.withNewTransactionReturning(emf, em -> getList(em, offset, size));
	}

	@Override
	public ListJson<P> getList(final long offset, final long size, final String whereClause, final ParamMap params) {
		return Transactions.withNewTransactionReturning(emf, em -> getList(em, offset, size, whereClause, params));
	}

	@Override
	public P create(final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> create(em, entity));
	}

	@Override
	public P create(final EntityManager em, final P entity) {
		LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
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
		LocalDateTime time = LocalDateTime.now(ZoneOffset.UTC);
		entity.setEditedOn(time);
		return em.merge(entity);
	}

	@Override
	public UpsertResult<P> upsert(final String whereClause, final ParamMap params, final P entity) {
		return Transactions.withNewTransactionReturning(emf,
				em -> upsert(em, getQuery(em, "", whereClause, params), entity));
	}

	@Override
	public UpsertResult<P> upsert(final EntityManager em, final String whereClause, final ParamMap params,
			final P entity) {
		return upsert(em, getQuery(em, "", whereClause, params), entity);
	}

	@Override
	public UpsertResult<P> upsert(final TypedQuery<P> query, final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> upsert(em, query, entity));
	}

	@Override
	public UpsertResult<P> upsert(final EntityManager em, final TypedQuery<P> query, final P entity) {
		boolean wasInserted = false;
		boolean wasUpdated = false;
		P e = firstOf(query);
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
			return getQuery(em, "", "o.id = :id", ParamMap.builder().parameter("id", id).build()).setParameter("id", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public ListJson<P> getList(final EntityManager em, final long offset, final long size) {
		return getList(em, offset, size, "", null);
	}

	@Override
	public ListJson<P> getList(final EntityManager em, final long offset, final long size, final String whereClause,
			final ParamMap params) {
		return getList(em, offset, size, null, whereClause, params);
	}

	@Override
	public ListJson<P> getList(final EntityManager em, final long offset, final long size, final String joinClause,
			final String whereClause, final ParamMap params) {
		return getList(em, offset, size, "o", joinClause, whereClause, params);
	}

	@Override
	public ListJson<P> getList(final EntityManager em, final long offset, final long size, String selectClause,
			String joinClause, String whereClause, final ParamMap params) {
		if (selectClause == null)
			selectClause = "o";
		if (whereClause == null)
			whereClause = "";
		if (joinClause == null)
			joinClause = "";
		ListJson<P> r = new ListJson<>();
		int s = Integer.MAX_VALUE;
		if (size < s)
			s = (int) size;
		int o = Integer.MAX_VALUE;
		if (offset < o)
			o = (int) offset;
		TypedQuery<P> q = getQuery(em, selectClause, joinClause, whereClause, params, type, null, false)
				.setMaxResults(s)
				.setFirstResult(o);
		List<P> qResult = q.getResultList();
		Query cq = getCountQuery(em, selectClause, joinClause, whereClause, params);
		Long cqResult = (Long) cq.getSingleResult();
		r.setEntries(qResult);
		r.setCount(cqResult);
		return r;
	}

	public TypedQuery<P> getQuery() {
		return Transactions.withNewTransactionReturning(emf, em -> getQuery(em, false));
	}

	public TypedQuery<P> getQuery(final boolean lockPessimistic) {
		return Transactions.withNewTransactionReturning(emf, em -> getQuery(em, lockPessimistic));
	}

	public TypedQuery<P> getQuery(final EntityManager em) {
		return getQuery(em, "", "", null, false);
	}

	public TypedQuery<P> getQuery(final EntityManager em, final boolean lockPessimistic) {
		return getQuery(em, "", "", null, lockPessimistic);
	}

	public TypedQuery<P> getQuery(final String joinClause, final String whereClause, final ParamMap params) {
		return Transactions.withNewTransactionReturning(emf,
				em -> getQuery(em, joinClause, whereClause, params, false));
	}

	public TypedQuery<P> getQuery(final String joinClause, final String whereClause, final ParamMap params,
			final boolean lockPessimistic) {
		return Transactions.withNewTransactionReturning(emf,
				em -> getQuery(em, joinClause, whereClause, params, lockPessimistic));
	}

	public TypedQuery<P> getQuery(final EntityManager em, final String joinClause, final String whereClause,
			final ParamMap params) {
		return getQuery(em, "o", joinClause, whereClause, params, type, "o.id ASC", false);
	}

	public TypedQuery<P> getQuery(final EntityManager em, final String joinClause, final String whereClause,
			final ParamMap params, final boolean lockPessimistic) {
		return getQuery(em, "o", joinClause, whereClause, params, type, "o.id ASC", lockPessimistic);
	}

	public <T> TypedQuery<T> getQuery(final String selectClause, final Class<T> type, final String orderBy) {
		return Transactions.withNewTransactionReturning(emf, em -> getQuery(em, selectClause, type, orderBy));
	}

	public <T> TypedQuery<T> getQuery(final String selectClause, final Class<T> type, final String orderBy,
			final boolean lockPessimistic) {
		return Transactions.withNewTransactionReturning(emf, em -> getQuery(em, selectClause, type, orderBy, false));
	}

	public <T> TypedQuery<T> getQuery(final EntityManager em, final String selectClause, final Class<T> type,
			final String orderBy) {
		return getQuery(em, selectClause, "", "", null, type, orderBy, false);
	}

	public <T> TypedQuery<T> getQuery(final EntityManager em, final String selectClause, final Class<T> type,
			final String orderBy, final boolean lockPessimistic) {
		return getQuery(em, selectClause, "", "", null, type, orderBy, lockPessimistic);
	}

	public <T> TypedQuery<T> getQuery(final String selectClause, final String joinClause, final String whereClause,
			final ParamMap params, final Class<T> type) {
		return Transactions.withNewTransactionReturning(emf,
				em -> getQuery(em, selectClause, joinClause, whereClause, params, type, "o.id ASC", false));
	}

	public <T> TypedQuery<T> getQuery(final String selectClause, final String joinClause, final String whereClause,
			final ParamMap params, final Class<T> type, final boolean lockPessimistic) {
		return Transactions.withNewTransactionReturning(emf,
				em -> getQuery(em, selectClause, joinClause, whereClause, params, type, "o.id ASC", lockPessimistic));
	}

	public <T> TypedQuery<T> getQuery(final EntityManager em, final String selectClause, final String joinClause,
			final String whereClause, final ParamMap params, final Class<T> type, final String orderBy,
			final boolean lockPessimistic) {
		String query = "SELECT " + selectClause + " FROM %s AS o";
		if (joinClause != null && !joinClause.isBlank())
			query += " " + joinClause;
		if (whereClause != null && !whereClause.isBlank())
			query += " WHERE " + whereClause;
		if (orderBy != null && !orderBy.isBlank())
			query += " ORDER BY " + orderBy;

		query = String.format(query, this.type.getSimpleName());

		String msg = query;
		if (params != null)
			for (Entry<String, Object> p : params.getParameters().entrySet())
				msg += "\\n  " + p.getKey() + ": " + p.getValue();
		log.debug(msg);

		TypedQuery<T> q = em.createQuery(query, type);
		if (lockPessimistic)
			q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		if (params != null)
			for (Entry<String, Object> e : params.getParameters().entrySet())
				q.setParameter(e.getKey(), e.getValue());
		return q;
	}

	public Query getCountQuery(final EntityManager em, final String selectClause, final String joinClause,
			final String whereClause, final ParamMap params) {
		String query = "SELECT COUNT(" + selectClause + ") FROM %s AS o";
		if (joinClause != null && !joinClause.isBlank())
			query += " " + joinClause;
		if (whereClause != null && !whereClause.isBlank())
			query += " WHERE " + whereClause;
		Query q = em.createQuery(String.format(query, this.type.getSimpleName()));
		if (params != null)
			for (Entry<String, Object> e : params.getParameters().entrySet())
				q.setParameter(e.getKey(), e.getValue());
		return q;
	}

	public P firstOf() {
		return firstOf(false);
	}

	public P firstOf(final boolean lockPessimistic) {
		return firstOf(null, null, lockPessimistic);
	}

	public P firstOf(final EntityManager em) {
		return firstOf(em, false);
	}

	public P firstOf(final EntityManager em, final boolean lockPessimistic) {
		return firstOf(em, null, null, lockPessimistic);
	}

	public P firstOf(final String whereClause, final ParamMap params) {
		return firstOf(whereClause, params, false);
	}

	public P firstOf(final String whereClause, final ParamMap params, final boolean lockPessimistic) {
		return Transactions.withNewTransactionReturning(emf, em -> firstOf(em, whereClause, params, lockPessimistic));
	}

	public P firstOf(final EntityManager em, final String whereClause, final ParamMap params) {
		return firstOf(em, whereClause, params, false);
	}

	public P firstOf(final EntityManager em, final String whereClause, final ParamMap params,
			final boolean lockPessimistic) {
		return firstOf(getQuery(em, "", whereClause, params, lockPessimistic));
	}

	public P firstOf(final TypedQuery<P> query) {
		List<P> r = query.setMaxResults(1).getResultList();
		if (r.size() == 1) {
			P jpa = r.get(0);
			return jpa;
		}
		return null;
	}

	public <T> T singleOf(final String selectClause, final String whereClause, final ParamMap params,
			final Class<T> type) {
		return singleOf(selectClause, whereClause, params, type, false);
	}

	public <T> T singleOf(final String selectClause, final String whereClause, final ParamMap params,
			final Class<T> type, final boolean lockPessimistic) {
		return Transactions.withNewTransactionReturning(emf,
				em -> singleOf(em, selectClause, whereClause, params, type, lockPessimistic));
	}

	public <T> T singleOf(final EntityManager em, final String selectClause, final String whereClause,
			final ParamMap params, final Class<T> type) {
		return singleOf(em, selectClause, whereClause, params, type, false);
	}

	public <T> T singleOf(final EntityManager em, final String selectClause, final String whereClause,
			final ParamMap params, final Class<T> type, final boolean lockPessimistic) {
		return singleOf(getQuery(em, selectClause, "", whereClause, params, type, null, lockPessimistic));
	}

	public <T> T singleOf(final TypedQuery<T> query) {
		return query.getSingleResult();
	}

	public List<P> listOf(final String whereClause, final ParamMap params) {
		return Transactions.withNewTransactionReturning(emf, em -> listOf(em, whereClause, params));
	}

	public List<P> listOf(final EntityManager em, final String whereClause, final ParamMap params) {
		return listOf(getQuery(em, "", whereClause, params));
	}

	public List<P> listOf(final TypedQuery<P> query) {
		return query.getResultList();
	}

	public List<P> reverseListOf(final String whereClause, final ParamMap params) {
		return Transactions.withNewTransactionReturning(emf, em -> reverseListOf(em, whereClause, params, false));
	}

	public List<P> reverseListOf(final String whereClause, final ParamMap params, final boolean lockPessimistric) {
		return Transactions.withNewTransactionReturning(emf,
				em -> reverseListOf(em, whereClause, params, lockPessimistric));
	}

	public List<P> reverseListOf(final EntityManager em, final String whereClause, final ParamMap params) {
		return reverseListOf(em, whereClause, params, false);
	}

	public List<P> reverseListOf(final EntityManager em, final String whereClause, final ParamMap params,
			final boolean lockPessimistic) {
		return reverseListOf(getQuery(em, "o", "", whereClause, params, type, "o.id DESC", lockPessimistic));
	}

	public List<P> reverseListOf(final TypedQuery<P> query) {
		return query.getResultList();
	}

	public List<P> firstNOf(final String whereClause, final long count, final ParamMap params) {
		return Transactions.withNewTransactionReturning(emf, em -> firstNOf(em, whereClause, count, params, false));
	}

	public List<P> firstNOf(final String whereClause, final long count, final ParamMap params,
			final boolean lockPessimistic) {
		return Transactions.withNewTransactionReturning(emf,
				em -> firstNOf(em, whereClause, count, params, lockPessimistic));
	}

	public List<P> firstNOf(final EntityManager em, final String whereClause, final long count, final ParamMap params) {
		return firstNOf(em, whereClause, count, params, false);
	}

	public List<P> firstNOf(final EntityManager em, final String whereClause, final long count, final ParamMap params,
			final boolean lockPessimistic) {
		return nOf(getQuery(em, "", whereClause, params, lockPessimistic), count);
	}

	public List<P> lastNOf(final String whereClause, final long count, final ParamMap params) {
		return lastNOf(whereClause, count, params, false);
	}

	public List<P> lastNOf(final String whereClause, final long count, final ParamMap params,
			final boolean lockPessimistic) {
		return Transactions.withNewTransactionReturning(emf,
				em -> lastNOf(em, whereClause, count, params, lockPessimistic));
	}

	public List<P> lastNOf(final EntityManager em, final String whereClause, final long count, final ParamMap params) {
		return lastNOf(em, whereClause, count, params, false);
	}

	public List<P> lastNOf(final EntityManager em, final String whereClause, final long count, final ParamMap params,
			final boolean lockPessimistic) {
		return nOf(getQuery(em, "o", "", whereClause, params, type, "o.id DESC", lockPessimistic), count);
	}

	public List<P> nOf(final String whereClause, final long count, final ParamMap params,
			final OrderByMap orderByFields) {
		return Transactions.withNewTransactionReturning(emf, em -> nOf(em, whereClause, count, params, orderByFields));
	}

	public List<P> nOf(final EntityManager em, final String whereClause, final long count, final ParamMap params,
			final OrderByMap orderByFields) {
		return nOf(em, whereClause, count, params, orderByFields, false);
	}

	public List<P> nOf(final EntityManager em, final String whereClause, final long count, final ParamMap params,
			final OrderByMap orderByFields, final boolean lockPessimistic) {
		String orderBy = "";
		for (Entry<String, Boolean> entry : orderByFields.getOrderByFields().entrySet()) {

			if (!orderBy.isEmpty())
				orderBy += ",";

			orderBy += "o." + entry.getKey();

			if (entry.getValue())
				orderBy += " DESC";
			else
				orderBy += " ASC";
		}
		if (orderBy.isEmpty())
			orderBy = "o.id ASC";
		return nOf(getQuery(em, "o", "", whereClause, params, type, orderBy, lockPessimistic), count);
	}

	public List<P> nOf(final TypedQuery<P> query, final long count) {
		int s = Integer.MAX_VALUE;
		if (count < s)
			s = (int) count;
		query.setMaxResults(s);
		return query.getResultList();
	}
}
