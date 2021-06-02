package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;

public class AsyncJpqlDao<P extends BasicAsyncJpa> extends BasicJpqlDao<P> {

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
	public AsyncJpqlDao(final EntityManagerFactory emf, final Class<P> type,
			final Class<? extends BasicJpa> tenantJpaType) {
		super(emf, type);
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
	public AsyncJpqlDao(final EntityManagerFactory emf, final Class<P> type,
			final Class<? extends BasicJpa> tenantJpaType, final String tenantReferenceFieldName,
			final String tenantIdFieldName) {
		super(emf, type);
		this.coreDao.tenantData = new TenantData(tenantJpaType, tenantReferenceFieldName, tenantIdFieldName);
	}

	/**
	 * Generates a DAO that lets you build and execute queries.
	 *
	 * @param emf  the {@link EntityManagerFactory} to use
	 * @param type the return-type of the query (the underlying JPA)
	 */
	public AsyncJpqlDao(final EntityManagerFactory emf, final Class<P> type) {
		super(emf, type);
	}

	/**
	 * Build a SELECT(*)-like query. The result will be of the underlying generic
	 * type.
	 *
	 * @return a query-builder
	 */
	public AsyncJpaListQueryBuilder<P> select() {
		return new AsyncJpaListQueryBuilder<>(emf, this, type);
	}

	/**
	 * Build a SELECT(*)-like query. The result will be of the given type (use this
	 * for a COUNT(*) query, for example).
	 *
	 * @param <T>        the type the result will be
	 * @param resultType the type the result will be
	 * @return a query-builder
	 */
	public <T> AsyncListQueryBuilder<P, T> select(final Class<T> resultType) {
		return new AsyncListQueryBuilder<>(emf, this, resultType);
	}

	/**
	 * Build a SELECT-query with a custom select-clause. The result will be of the
	 * given type (use this for a COUNT(*) query, for example).
	 *
	 * @param <T>          the type the result will be
	 * @param selectClause your custom select-clause (the base-object has the alias
	 *                     'o'. So the default would be "o" internally resulting in
	 *                     a "SELECT o")
	 * @param resultType   the type the result will be
	 * @return a query-builder
	 */
	public <T> AsyncListQueryBuilder<P, T> select(final String selectClause, final Class<T> resultType) {
		AsyncListQueryBuilder<P, T> b = new AsyncListQueryBuilder<>(emf, this, resultType);
		b.setSelect(selectClause);
		return b;
	}

	/**
	 * Build a SELECT-query with a custom select-clause. The result will be of the
	 * underlying generic type.
	 *
	 * @param selectClause your custom select-clause (the base-object has the alias
	 *                     'o'. So the default would be "o" internally resulting in
	 *                     a "SELECT o")
	 * @return a query-builder
	 */
	public AsyncJpaListQueryBuilder<P> select(final String selectClause) {
		AsyncJpaListQueryBuilder<P> b = new AsyncJpaListQueryBuilder<>(emf, this, type);
		b.setSelect(selectClause);
		return b;
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
