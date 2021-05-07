package info.unterrainer.commons.httpserver.daos;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Query<P extends BasicJpa, T> {

	protected final EntityManagerFactory emf;
	protected final QueryBuilder<P, T> builder;

	private <V> V wrap(final Function<EntityManager, V> func) {
		if (builder.entityManager == null)
			return Transactions.withNewTransactionReturning(emf, em -> func.apply(em));
		return func.apply(builder.entityManager);
	}

	/**
	 * Gets all the rows as a {@link ListJson} as returned by a web-service.
	 * <p>
	 * The list contains additional information about the data.
	 *
	 * @return a {@link ListJson} containing the rows as specified
	 */
	public ListJson<T> getListJson() {
		return wrap(em -> {
			ListJson<T> r = new ListJson<>();
			r.setEntries(getList(em));
			r.setCount((Long) builder.getCountQuery(em).getSingleResult());
			return r;
		});
	}

	/**
	 * Gets all the rows specified by offset and size as a {@link ListJson} as
	 * returned by a web-service.
	 * <p>
	 * The list contains additional information about the data.
	 *
	 * @param offset the row-number to start at
	 * @param size   the number-of-rows to return
	 * @return a {@link ListJson} containing the rows as specified
	 */
	public ListJson<T> getListJson(final long offset, final long size) {
		ListJson<T> r = new ListJson<>();
		r.setEntries(getList(offset, size));
		r.setCount((Long) countQuery.getSingleResult());
		return r;
	}

	/**
	 * Gets the first row this query returns.
	 *
	 * @return the result-row as a JPA
	 */
	public T getFirst() {
		List<T> r = getList(0, 1);
		if (r.size() == 1) {
			T jpa = r.get(0);
			return jpa;
		}
		return null;
	}

	/**
	 * Get the first N rows from this queries' results.
	 *
	 * @param count the number of rows to get
	 * @return the list of the first N result-rows as JPAs
	 */
	public List<T> getN(final long count) {
		return getList(0, count);
	}

	/**
	 * Execute a SELECT query that returns a single result-row.
	 *
	 * @return the result-row as a JPA
	 * @throws NoResultException        if there is no
	 *                                  resultNonUniqueResultException
	 * @throws NonUniqueResultException if more than one result
	 */
	public T getSingle() {
		return typedQuery.getSingleResult();
	}

	/**
	 * Execute a SELECT query that returns multiple result-rows.
	 *
	 * @return the list of result-rows as JPAs
	 */
	public List<T> getList() {
		return wrap(em -> getList(em));
	}

	private List<T> getList(final EntityManager em) {
		return getList(0, Long.MAX_VALUE);
	}

	/**
	 * Gets all the rows specified by offset and size as a list.
	 *
	 * @param offset the row-number to start at
	 * @param size   the number-of-rows to return
	 * @return a list containing the rows as specified
	 */
	public List<T> getList(final long offset, final long size) {
		return wrap(em -> getList(em, offset, size));
	}

	private List<T> getList(final EntityManager em, final long offset, final long size) {
		int s = Integer.MAX_VALUE;
		if (size < s)
			s = (int) size;
		int o = Integer.MAX_VALUE;
		if (offset < o)
			o = (int) offset;
		TypedQuery<T> query = builder.getTypedQuery(em);
		query.setFirstResult(o);
		query.setMaxResults(s);
		return query.getResultList();
	}

	/**
	 * Execute a SELECT query that returns multiple result-rows and reverse the
	 * order of results.
	 * <p>
	 * The reversing-process happens after retrieval of the result, since the whole
	 * list is going to be transferred anyway.<br>
	 * The list is reversed using {@code Collections.reverse(list)};
	 *
	 * @return the reversed list of result-rows as JPAs
	 */
	public List<T> getListReversed() {
		List<T> l = typedQuery.getResultList();
		Collections.reverse(l);
		return l;
	}
}
