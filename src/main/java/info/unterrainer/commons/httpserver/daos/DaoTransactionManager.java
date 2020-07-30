package info.unterrainer.commons.httpserver.daos;

public interface DaoTransactionManager<E> {

	/**
	 * Gets you a new transaction and starts it.
	 *
	 * @return a {@link JpqlTransaction}
	 */
	DaoTransaction<E> beginTransaction();
}
