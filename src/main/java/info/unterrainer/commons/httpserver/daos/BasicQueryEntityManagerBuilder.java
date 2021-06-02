package info.unterrainer.commons.httpserver.daos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;

import org.eclipse.jetty.webapp.WebAppContext.Context;

import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BasicQueryEntityManagerBuilder<P extends BasicJpa, T, R extends BasicQueryEntityManagerBuilder<P, T, R>> {

	@Getter
	protected EntityManager entityManager;
	@Getter
	protected Set<Long> tenantIds;
	protected Set<Long> writeTenantIds;

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

	/**
	 * Set custom tenant-IDs to be used when querying / writing to the database.
	 * <p>
	 * Overwrites the existing set.
	 *
	 * @param id the tenant-ID to use
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R tenant(final Long... ids) {
		tenantIds = new HashSet<>(Arrays.asList(ids));
		return (R) this;
	}

	/**
	 * Set a custom tenant-ID that is retrieved from the given context to be used
	 * when querying the database.
	 * <p>
	 * Overwrites the existing set.
	 *
	 * @param ctx the context that contains the tenant-ID to use
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R tenant(final Context ctx) {
		tenantIds = (Set<Long>) ctx.getAttribute(Attribute.USER_TENANTS_READ_SET);
		writeTenantIds = (Set<Long>) ctx.getAttribute(Attribute.USER_TENANTS_WRITE_SET);
		return (R) this;
	}
}
