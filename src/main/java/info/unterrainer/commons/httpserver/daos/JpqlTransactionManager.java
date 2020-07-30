package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpqlTransactionManager implements DaoTransactionManager<EntityManager> {

	protected final EntityManagerFactory emf;

	@Override
	public DaoTransaction<EntityManager> beginTransaction() {
		EntityManager em = emf.createEntityManager();
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		return new JpqlTransaction(em);
	}
}
