package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BasicQueryEntityManagerBuilder<P extends BasicJpa, T, R extends BasicQueryEntityManagerBuilder<P, T, R>> {

	@Getter
	protected EntityManager entityManager;

	/**
	 * Sets a custom {@link EntityManager}.
	 * <p>
	 * Default is to create one when creating the query.<br>
	 * To reset it to default, set it to null.
	 *
	 * @param em an {@link EntityManager}
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R entityManager(final EntityManager em) {
		entityManager = em;
		return (R) this;
	}
}
