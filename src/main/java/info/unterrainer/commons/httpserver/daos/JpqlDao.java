package info.unterrainer.commons.httpserver.daos;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.daos.UpsertResult.UpsertResultBuilder;
import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpqlDao<P extends BasicJpa> implements BasicDao<P> {

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
	public P create(final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> create(em, entity));
	}

	@Override
	public P create(final EntityManager em, final P entity) {
		LocalDateTime time = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
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
		LocalDateTime time = ZonedDateTime.now(ZoneOffset.UTC).toLocalDateTime();
		entity.setEditedOn(time);
		return em.merge(entity);
	}

	@Override
	public UpsertResult<P> upsert(final String whereClause, final ParamMap params, final P entity) {
		return Transactions.withNewTransactionReturning(emf,
				em -> upsert(em, getQuery(em, whereClause, params), entity));
	}

	@Override
	public UpsertResult<P> upsert(final EntityManager em, final String whereClause, final ParamMap params,
			final P entity) {
		return upsert(em, getQuery(em, whereClause, params), entity);
	}

	@Override
	public UpsertResult<P> upsert(final TypedQuery<P> query, final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> upsert(em, query, entity));
	}

	@Override
	public UpsertResult<P> upsert(final EntityManager em, final TypedQuery<P> query, final P entity) {
		UpsertResultBuilder<P, ?, ?> builder = UpsertResult.builder();

		P e = firstResultOf(query);
		if (e == null) {
			e = create(em, entity);
			builder.wasInserted(true);
		} else {
			e = update(em, entity);
			builder.wasUpdated(true);
		}
		return builder.jpa(e).build();
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
		return getQuery(em, "o.id = :id", ParamMap.builder().parameter("id", id).build()).setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public ListJson<P> getList(final EntityManager em, final long offset, final long size) {
		ListJson<P> r = new ListJson<>();
		TypedQuery<P> q = getQuery(em).setMaxResults((int) size).setFirstResult((int) offset);
		List<P> qResult = q.getResultList();
		Query cq = em.createQuery(String.format("SELECT COUNT(o.id) FROM %s AS o", type.getSimpleName()));
		Long cqResult = (Long) cq.getSingleResult();
		r.setEntries(qResult);
		r.setCount(cqResult);
		return r;
	}

	public TypedQuery<P> getQuery(final EntityManager em) {
		return getQuery(em, "", null);
	}

	public TypedQuery<P> getQuery(final EntityManager em, final String whereClause, final ParamMap params) {
		String query = "SELECT o FROM %s AS o";
		if (whereClause != null && !whereClause.isBlank())
			query += " WHERE " + whereClause;
		TypedQuery<P> q = em.createQuery(String.format(query + " ORDER BY o.id ASC", type.getSimpleName()), type);
		if (params != null)
			for (Entry<String, Object> e : params.getParameters().entrySet())
				q.setParameter(e.getKey(), e.getValue());
		return q;
	}

	public P firstResultOf(final EntityManager em, final String whereClause, final ParamMap params) {
		return firstResultOf(getQuery(em, whereClause, params));
	}

	public P firstResultOf(final TypedQuery<P> query) {
		List<P> r = query.setMaxResults(1).getResultList();
		if (r.size() == 1) {
			P jpa = r.get(0);
			return jpa;
		}
		return null;
	}

	public List<P> allResultsOf(final EntityManager em, final String whereClause, final ParamMap params) {
		return allResultsOf(getQuery(em, whereClause, params));
	}

	public List<P> allResultsOf(final TypedQuery<P> query) {
		return query.getResultList();
	}
}
