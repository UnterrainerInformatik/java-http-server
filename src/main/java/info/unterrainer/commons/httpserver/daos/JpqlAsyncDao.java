package info.unterrainer.commons.httpserver.daos;

import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;
import info.unterrainer.commons.rdbutils.enums.AsyncState;

public class JpqlAsyncDao<P extends BasicAsyncJpa> extends JpqlDao<P> implements BasicAsyncDao<P> {

	public JpqlAsyncDao(final EntityManagerFactory emf, final Class<P> type) {
		super(emf, type);
	}

	@Override
	public P getLastWith(final AsyncState... states) {
		return Transactions.withNewTransactionReturning(emf, em -> getLastWith(em, states));
	}

	@Override
	public P getLastWith(final EntityManager em, final AsyncState... states) {
		return internalGet(em, false, false, null, states);
	}

	@Override
	public ListJson<P> getLastNWith(final Long count, final AsyncState... states) {
		return Transactions.withNewTransactionReturning(emf, em -> getLastNWith(em, count, states));
	}

	@Override
	public ListJson<P> getLastNWith(final EntityManager em, final Long count, final AsyncState... states) {
		return internalGetList(em, count, false, false, null, states);
	}

	@Override
	public P getNextWith(final AsyncState... states) {
		return Transactions.withNewTransactionReturning(emf, em -> getNextWith(em, states));
	}

	@Override
	public P getNextWith(final EntityManager em, final AsyncState... states) {
		return internalGet(em, false, true, null, states);
	}

	@Override
	public ListJson<P> getNextNWith(final Long count, final AsyncState... states) {
		return Transactions.withNewTransactionReturning(emf, em -> getNextNWith(em, count, states));
	}

	@Override
	public ListJson<P> getNextNWith(final EntityManager em, final Long count, final AsyncState... states) {
		return internalGetList(em, count, false, true, null, states);
	}

	@Override
	public P lockedGetNextWith(final AsyncState stateToSetTo, final AsyncState... states) {
		return Transactions.withNewTransactionReturning(emf, em -> lockedGetNextWith(em, stateToSetTo, states));
	}

	@Override
	public P lockedGetNextWith(final EntityManager em, final AsyncState stateToSetTo, final AsyncState... states) {
		return internalGet(em, true, true, stateToSetTo, states);
	}

	@Override
	public ListJson<P> lockedGetNextNWith(final Long count, final AsyncState stateToSetTo, final AsyncState... states) {
		return Transactions.withNewTransactionReturning(emf, em -> lockedGetNextNWith(em, count, stateToSetTo, states));
	}

	@Override
	public ListJson<P> lockedGetNextNWith(final EntityManager em, final Long count, final AsyncState stateToSetTo,
			final AsyncState... states) {
		return internalGetList(em, count, true, true, stateToSetTo, states);
	}

	public P internalGet(final EntityManager em, final boolean lockPessimistic, final boolean ascending,
			final AsyncState stateToSetTo, final AsyncState... states) {
		ListJson<P> list = internalGetList(em, 1, lockPessimistic, ascending, stateToSetTo, states);
		if (list.getCount() == 0)
			return null;
		return list.getEntries().get(0);
	}

	public ListJson<P> internalGetList(final EntityManager em, final long size, final boolean lockPessimistic,
			final boolean ascending, final AsyncState stateToSetTo, final AsyncState... states) {
		int s = Integer.MAX_VALUE;
		if (size < s)
			s = (int) size;

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT o FROM %s AS o ");
		boolean isFirst = true;
		for (int i = 0; i < states.length; i++) {
			if (isFirst) {
				sb.append("WHERE ");
				isFirst = false;
			} else
				sb.append("OR ");
			sb.append("state=:state");
			sb.append(i);
			sb.append(" ");
		}
		sb.append("ORDER BY o.id " + (ascending ? "ASC" : "DESC"));

		TypedQuery<P> q = em.createQuery(String.format(sb.toString(), type.getSimpleName()), type).setMaxResults(s);
		if (lockPessimistic)
			q.setLockMode(LockModeType.PESSIMISTIC_WRITE);

		for (int i = 0; i < states.length; i++)
			q.setParameter("state" + i, states[i]);

		List<P> r = q.getResultList();

		if (stateToSetTo != null)
			for (P jpa : r) {
				jpa.setState(stateToSetTo);
				em.merge(jpa);
			}

		return ListJson.<P>builder().count(r.size()).entries(r).build();
	}

	@Override
	public P setStateTo(final AsyncState stateToSetTo, final Long id) {
		return setStateTo(stateToSetTo, id, null);
	}

	@Override
	public P setStateTo(final EntityManager em, final AsyncState stateToSetTo, final Long id) {
		return setStateTo(em, stateToSetTo, id, null);
	}

	@Override
	public P setStateTo(final AsyncState stateToSetTo, final Long id, final Function<P, P> additionalTransformations) {
		return Transactions.withNewTransactionReturning(emf,
				em -> setStateTo(em, stateToSetTo, id, additionalTransformations));
	}

	@Override
	public P setStateTo(final EntityManager em, final AsyncState stateToSetTo, final Long id,
			final Function<P, P> additionalTransformations) {
		P jpa = getById(em, id);
		jpa.setState(stateToSetTo);
		if (additionalTransformations != null)
			jpa = additionalTransformations.apply(jpa);
		update(em, jpa);
		return jpa;
	}
}
