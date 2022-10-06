package info.unterrainer.commons.httpserver.daos;

import java.util.function.Function;

import info.unterrainer.commons.httpserver.jpas.BasicPermissionJpa;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class JpqlDao<P extends BasicJpa> extends BasicJpqlDao<P> {

	/**
	 * Generates a DAO that lets you build and execute queries.
	 * <p>
	 * This dao has a tenant-permission table attached and may retrieve and write
	 * data per tenant.
	 * <p>
	 * {@code tenantReferenceFieldName} defaults to {@code referenceId}<br>
	 * {@code tenantReferenceFieldName} defaults to {@code tenantId}
	 *
	 * @param emf           the {@link EntityManagerFactory} to use
	 * @param type          the return-type of the query (the underlying JPA)
	 * @param tenantJpaType the JPA of the tenant-permission table associated
	 */
	public JpqlDao(final EntityManagerFactory emf, final Class<P> type,
			final Class<? extends BasicPermissionJpa> tenantJpaType) {
		super(emf, type);
		this.coreDao.tenantData = new TenantData(tenantJpaType);
	}

	/**
	 * Generates a DAO that lets you build and execute queries.
	 * <p>
	 * This dao has a tenant-permission table attached and may retrieve and write
	 * data per tenant.
	 * <p>
	 * {@code tenantReferenceFieldName} defaults to {@code referenceId}<br>
	 * {@code tenantReferenceFieldName} defaults to {@code tenantId}
	 *
	 * @param entityManagerFactorySupplier a supplier providing the correct
	 *                                     {@link EntityManagerFactory} based on a
	 *                                     given {@link Context}
	 * @param type                         the return-type of the query (the
	 *                                     underlying JPA)
	 * @param tenantJpaType                the JPA of the tenant-permission table
	 *                                     associated
	 */
	public JpqlDao(final Function<Context, EntityManagerFactory> entityManagerFactorySupplier, final Class<P> type,
			final Class<? extends BasicPermissionJpa> tenantJpaType) {
		super(entityManagerFactorySupplier, type);
		this.coreDao.tenantData = new TenantData(tenantJpaType);
	}

	/**
	 * Generates a DAO that lets you build and execute queries.
	 * <p>
	 * This dao has a tenant-permission table attached and may retrieve and write
	 * data per tenant.
	 *
	 * @param emf                      the {@link EntityManagerFactory} to use
	 * @param type                     the return-type of the query (the underlying
	 *                                 JPA)
	 * @param tenantJpaType            the JPA of the tenant-permission table
	 *                                 associated
	 * @param tenantReferenceFieldName the name of the field holding the reference
	 *                                 to the main-table-id
	 * @param tenantIdFieldName        the name of the field holding the tenant-ID
	 */
	public JpqlDao(final EntityManagerFactory emf, final Class<P> type,
			final Class<? extends BasicPermissionJpa> tenantJpaType, final String tenantReferenceFieldName,
			final String tenantIdFieldName) {
		super(emf, type);
		this.coreDao.tenantData = new TenantData(tenantJpaType, tenantReferenceFieldName, tenantIdFieldName);
	}

	/**
	 * Generates a DAO that lets you build and execute queries.
	 * <p>
	 * This dao has a tenant-permission table attached and may retrieve and write
	 * data per tenant.
	 *
	 * @param entityManagerFactorySupplier a supplier providing the correct
	 *                                     {@link EntityManagerFactory} based on a
	 *                                     given {@link Context}
	 * @param type                         the return-type of the query (the
	 *                                     underlying JPA)
	 * @param tenantJpaType                the JPA of the tenant-permission table
	 *                                     associated
	 * @param tenantReferenceFieldName     the name of the field holding the
	 *                                     reference to the main-table-id
	 * @param tenantIdFieldName            the name of the field holding the
	 *                                     tenant-ID
	 */
	public JpqlDao(final Function<Context, EntityManagerFactory> entityManagerFactorySupplier, final Class<P> type,
			final Class<? extends BasicPermissionJpa> tenantJpaType, final String tenantReferenceFieldName,
			final String tenantIdFieldName) {
		super(entityManagerFactorySupplier, type);
		this.coreDao.tenantData = new TenantData(tenantJpaType, tenantReferenceFieldName, tenantIdFieldName);
	}

	/**
	 * Generates a DAO that lets you build and execute queries.
	 *
	 * @param emf  the {@link EntityManagerFactory} to use
	 * @param type the return-type of the query (the underlying JPA)
	 */
	public JpqlDao(final EntityManagerFactory emf, final Class<P> type) {
		super(emf, type);
	}

	/**
	 * Generates a DAO that lets you build and execute queries.
	 *
	 * @param entityManagerFactorySupplier a supplier providing the correct
	 *                                     {@link EntityManagerFactory} based on a
	 *                                     given {@link Context}
	 * @param type                         the return-type of the query (the
	 *                                     underlying JPA)
	 */
	public JpqlDao(final Function<Context, EntityManagerFactory> entityManagerFactorySupplier, final Class<P> type) {
		super(entityManagerFactorySupplier, type);
	}

	/**
	 * Build a SELECT(*)-like query. The result will be of the underlying generic
	 * type.
	 *
	 * @return a query-builder
	 */
	public JpaListQueryBuilder<P> select() {
		return new JpaListQueryBuilder<>(emf, this, type);
	}

	/**
	 * Build a SELECT(*)-like query. The result will be of the given type (use this
	 * for a COUNT(*) query, for example).
	 *
	 * @param <T>        the type the result will be
	 * @param resultType the type the result will be
	 * @return a query-builder
	 */
	public <T> ListQueryBuilder<P, T> select(final Class<T> resultType) {
		return new ListQueryBuilder<>(emf, this, resultType);
	}

	/**
	 * Get an element by ID.
	 *
	 * @param id the ID to fetch.
	 * @return the element with the given ID or null, if there was no such thing
	 */
	public SingleQueryBuilder<P, P> select(final Long id) {
		return new SingleQueryBuilder<>(this, id);
	}

	/**
	 * Insert the given entity.
	 *
	 * @param entity to insert.
	 * @return the entity after inserting
	 */
	public InsertQueryBuilder<P> insert(final P entity) {
		return new InsertQueryBuilder<>(this, entity);
	}

	/**
	 * Update the given entity.
	 *
	 * @param entity to update.
	 * @return the entity after updating
	 */
	public UpdateQueryBuilder<P> update(final P entity) {
		return new UpdateQueryBuilder<>(this, entity);
	}
}
