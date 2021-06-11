package info.unterrainer.commons.httpserver.daos;

import java.util.function.Function;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import io.javalin.http.Context;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpqlTransactionManager implements DaoTransactionManager<EntityManager> {

	protected final EntityManagerFactory emf;
	protected final Function<Context, EntityManagerFactory> entityManagerFactorySupplier;

	@Override
	public DaoTransaction<EntityManager> beginTransaction(final Context ctx) {
		EntityManagerFactory emf = this.emf;
		if (entityManagerFactorySupplier != null)
			emf = entityManagerFactorySupplier.apply(ctx);
		EntityManager em = emf.createEntityManager();
		if (!em.getTransaction().isActive())
			em.getTransaction().begin();
		return new JpqlTransaction(em);
	}
}
