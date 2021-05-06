package info.unterrainer.commons.httpserver.daos;

import java.time.LocalDateTime;
import java.util.Map.Entry;

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
	public ListJson<P> getList(final EntityManager em, final long offset, final long size, final String selectClause,
			final String joinClause, final String whereClause, final ParamMap params, final String orderByClause) {
		return query().entityManager(em)
				.select(selectClause)
				.where(whereClause)
				.join(joinClause)
				.orderBy(orderByClause)
				.parameters(params)
				.build()
				.getList(offset, size);
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

	@Override
	public UpsertResult<P> upsert(final String whereClause, final ParamMap params, final P entity) {
		return Transactions.withNewTransactionReturning(emf,
				em -> upsert(em, query().entityManager(em).where(whereClause).parameters(params).build(), entity));
	}

	@Override
	public UpsertResult<P> upsert(final EntityManager em, final String whereClause, final ParamMap params,
			final P entity) {
		return upsert(em, query().entityManager(em).where(whereClause).parameters(params).build(), entity);
	}

	@Override
	public UpsertResult<P> upsert(final info.unterrainer.commons.httpserver.daos.Query<P, P> query, final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> upsert(em, query, entity));
	}

	@Override
	public UpsertResult<P> upsert(final EntityManager em,
			final info.unterrainer.commons.httpserver.daos.Query<P, P> query, final P entity) {
		boolean wasInserted = false;
		boolean wasUpdated = false;
		P e = query.getFirst();
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
			return query().entityManager(em).where("o.id=:id").addParam("id", id).build().getSingle();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public <T> QueryBuilder<P, T> query(final Class<T> resultType) {
		return new QueryBuilder<>(emf, this, resultType);
	}

	@Override
	public QueryBuilder<P, P> query() {
		return new QueryBuilder<>(emf, this, type);
	}

	@Override
	public CountQueryBuilder<P, P> countQuery() {
		return new CountQueryBuilder<>(emf, this);
	}

	<T> TypedQuery<T> getQuery(final EntityManager em, final String selectClause, final String joinClause,
			final String whereClause, final ParamMap params, final Class<T> type, final String orderBy,
			final boolean lockPessimistic) {
		String query = "SELECT ";
		if (selectClause == null || selectClause.isBlank())
			query += "o";
		else
			query += selectClause;
		query += " FROM %s AS o";

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

		@SuppressWarnings("unchecked")
		Class<T> t = (Class<T>) this.type;
		if (type != null)
			t = type;

		TypedQuery<T> q = em.createQuery(query, t);
		if (lockPessimistic)
			q.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		if (params != null)
			for (Entry<String, Object> e : params.getParameters().entrySet())
				q.setParameter(e.getKey(), e.getValue());
		return q;
	}

	Query getCountQuery(final EntityManager em, final String selectClause, final String joinClause,
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
}
