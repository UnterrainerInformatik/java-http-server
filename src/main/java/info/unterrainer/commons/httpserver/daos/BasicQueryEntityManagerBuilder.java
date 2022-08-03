package info.unterrainer.commons.httpserver.daos;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;

import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import io.javalin.http.Context;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BasicQueryEntityManagerBuilder<P extends BasicJpa, T, R extends BasicQueryEntityManagerBuilder<P, T, R>> {

	@Getter
	protected EntityManager entityManager;
	@Getter
	protected Set<Long> readTenantIds;
	@Getter
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
	 * Set custom tenant-IDs to be used when querying the database.
	 * <p>
	 * Overwrites the existing set.
	 *
	 * @param ids the tenant-IDs to use
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R readTenant(final Long... ids) {
		readTenantIds = new HashSet<>(Arrays.asList(ids));
		return (R) this;
	}

	/**
	 * Set custom tenant-IDs to be used when querying the database.
	 * <p>
	 * Overwrites the existing set.
	 *
	 * @param ids the tenant-IDs to use
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R readTenant(final Collection<Long> ids) {
		readTenantIds = new HashSet<>(ids);
		return (R) this;
	}

	/**
	 * Set custom tenant-IDs to be used when querying the database.
	 * <p>
	 * Overwrites the existing set.
	 *
	 * @param commaSeparatedList the tenant-IDs to use in the form of a
	 *                           comma-separated list
	 * @return an instance of this builder to provide a fluent interface
	 */
	public R readTenant(final String commaSeparatedList) {
		return readTenant(createSetFrom(commaSeparatedList));
	}

	/**
	 * Set custom tenant-IDs to be used when writing to the database.
	 * <p>
	 * Overwrites the existing set.
	 *
	 * @param ids the tenant-IDs to use
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R writeTenant(final Long... ids) {
		writeTenantIds = new HashSet<>(Arrays.asList(ids));
		return (R) this;
	}

	/**
	 * Set custom tenant-IDs to be used when writing to the database.
	 * <p>
	 * Overwrites the existing set.
	 *
	 * @param ids the tenant-IDs to use
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R writeTenant(final Collection<Long> ids) {
		writeTenantIds = new HashSet<>(ids);
		return (R) this;
	}

	/**
	 * Set custom tenant-IDs to be used when writing to the database.
	 * <p>
	 * Overwrites the existing set.
	 *
	 * @param commaSeparatedList the tenant-IDs to use in the form of a
	 *                           comma-separated list
	 * @return an instance of this builder to provide a fluent interface
	 */
	public R writeTenant(final String commaSeparatedList) {
		return writeTenant(createSetFrom(commaSeparatedList));
	}

	/**
	 * Set a custom tenant-ID that is retrieved from the given context to be used
	 * when querying the database.
	 * <p>
	 * Overwrites the existing sets of {@link #readTenant(Long...)} and
	 * {@link #writeTenant(Long...)}.
	 *
	 * @param ctx the context that contains the tenant-IDs to use (read and write)
	 * @return an instance of this builder to provide a fluent interface
	 */
	@SuppressWarnings("unchecked")
	public R tenant(final Context ctx) {
		readTenantIds = (Set<Long>) ctx.attribute(Attribute.USER_TENANTS_READ_SET);
		writeTenantIds = (Set<Long>) ctx.attribute(Attribute.USER_TENANTS_WRITE_SET);
		return (R) this;
	}

	private Set<Long> createSetFrom(final String commaSeparatedList) {
		if (commaSeparatedList == null || commaSeparatedList.isBlank())
			return new HashSet<>();

		return Arrays.stream(commaSeparatedList.split(","))
				.map(this::parseLong)
				.filter(java.util.Objects::nonNull)
				.collect(Collectors.toSet());
	}

	private Long parseLong(final String s) {
		if (s == null || s.isBlank())
			return null;
		try {
			return Long.parseLong(s.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
