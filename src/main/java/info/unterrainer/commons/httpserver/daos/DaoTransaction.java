package info.unterrainer.commons.httpserver.daos;

public interface DaoTransaction<M> {

	void end();

	M getManager();
}
