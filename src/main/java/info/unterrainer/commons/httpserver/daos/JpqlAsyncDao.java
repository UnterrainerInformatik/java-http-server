package info.unterrainer.commons.httpserver.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;
import info.unterrainer.commons.rdbutils.enums.AsyncState;

public class JpqlAsyncDao<P extends BasicAsyncJpa> extends JpqlDao<P> implements BasicAsyncDao<P> {

	public JpqlAsyncDao(final EntityManagerFactory emf, final Class<P> type) {
		super(emf, type);
	}

	@Override
	public P lockedGetNextWith(final AsyncState stateToSetTo, final AsyncState... states) {
		return Transactions.withNewTransactionReturning(emf, em -> lockedGetNextWith(em, stateToSetTo, states));
	}

	@Override
	public P lockedGetNextWith(final EntityManager em, final AsyncState stateToSetTo, final AsyncState... states) {
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
		sb.append("ORDER BY o.id ASC");

		TypedQuery<P> q = em.createQuery(String.format(sb.toString(), type.getSimpleName()), type)
				.setMaxResults(1)
				.setLockMode(LockModeType.PESSIMISTIC_WRITE);

		for (int i = 0; i < states.length; i++)
			q.setParameter("state" + i, states[i].name());

		List<P> r = q.getResultList();
		if (r.size() == 1) {
			P jpa = r.get(0);
			jpa.setState(stateToSetTo);
			em.merge(jpa);
			return jpa;
		}
		return null;
	}

	@Override
	public P setStateTo(final AsyncState stateToSetTo, final Long id) {
		return Transactions.withNewTransactionReturning(emf, em -> setStateTo(em, stateToSetTo, id));
	}

	@Override
	public P setStateTo(final EntityManager em, final AsyncState stateToSetTo, final Long id) {
		P jpa = getById(em, id);
		jpa.setState(stateToSetTo);
		persist(em, jpa);
		return jpa;
	}
}
