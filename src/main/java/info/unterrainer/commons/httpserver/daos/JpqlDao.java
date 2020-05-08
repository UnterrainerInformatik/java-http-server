package info.unterrainer.commons.httpserver.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
		em.persist(entity);
		return entity;
	}

	@Override
	public P persist(final P entity) {
		return Transactions.withNewTransactionReturning(emf, em -> em.merge(entity));
	}

	@Override
	public P persist(final EntityManager em, final P entity) {
		return em.merge(entity);
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
		return em.createQuery(String.format("SELECT o FROM %s AS o WHERE o.id = :id", type.getSimpleName()), type)
				.setParameter("id", id)
				.getSingleResult();
	}

	@Override
	public ListJson<P> getList(final EntityManager em, final long offset, final long size) {
		ListJson<P> r = new ListJson<>();
		TypedQuery<P> q = em
				.createQuery(String.format("SELECT o FROM %s AS o ORDER BY o.id ASC", type.getSimpleName()), type)
				.setMaxResults((int) size)
				.setFirstResult((int) offset);
		List<P> qResult = q.getResultList();
		Query cq = em.createQuery(String.format("SELECT COUNT(o.id) FROM %s AS o", type.getSimpleName()));
		Long cqResult = (Long) cq.getSingleResult();
		r.setEntries(qResult);
		r.setCount(cqResult);
		return r;
	}
}
