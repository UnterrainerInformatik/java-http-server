package info.unterrainer.commons.httpserver.daos;

import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpqlTransaction implements DaoTransaction<EntityManager> {

	protected final EntityManager em;

	@Override
	public void end() {
		try {
			em.flush();
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction() != null && em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	@Override
	public EntityManager getManager() {
		return em;
	}
}
