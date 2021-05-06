package info.unterrainer.commons.httpserver.daos;

import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class Query<P extends BasicJpa, T> {

	protected final JpqlDao<P> dao;
	@Getter
	protected final TypedQuery<T> typedQuery;
	@Getter
	protected final javax.persistence.Query countQuery;

	/**
	 * Gets all the rows specified by offset and size as a list.
	 *
	 * @param offset the row-number to start at
	 * @param size   the number-of-rows to return
	 * @return a {@link ListJson} containing the rows as specified
	 */
	public ListJson<T> getList(final long offset, final long size) {
		ListJson<T> r = new ListJson<>();
		int s = Integer.MAX_VALUE;
		if (size < s)
			s = (int) size;
		int o = Integer.MAX_VALUE;
		if (offset < o)
			o = (int) offset;
		List<T> qResult = typedQuery.getResultList();
		Long cqResult = (Long) countQuery.getSingleResult();
		r.setEntries(qResult);
		r.setCount(cqResult);
		return r;
	}

	/**
	 * Gets the first row this query returns.
	 *
	 * @return the result-row as a JPA
	 */
	public T getFirst() {
		List<T> r = typedQuery.setMaxResults(1).getResultList();
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
		int s = Integer.MAX_VALUE;
		if (count < s)
			s = (int) count;
		typedQuery.setMaxResults(s);
		return typedQuery.getResultList();
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
		return typedQuery.getResultList();
	}

	/**
	 * Execute a SELECT query that returns multiple result-rows and reverse the
	 * order of results.
	 *
	 * @return the reversed list of result-rows as JPAs
	 */
	public List<T> getListReversed() {
		List<T> l = typedQuery.getResultList();
		Collections.reverse(l);
		return l;
	}
}
