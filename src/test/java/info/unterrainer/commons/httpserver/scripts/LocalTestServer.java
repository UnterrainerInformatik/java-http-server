package info.unterrainer.commons.httpserver.scripts;

import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.httpserver.HttpServer;
import info.unterrainer.commons.httpserver.daos.JpqlDao;
import info.unterrainer.commons.httpserver.enums.Endpoint;
import info.unterrainer.commons.httpserver.scripts.handlers.StatusHandler;
import info.unterrainer.commons.httpserver.scripts.jpas.TestJpa;
import info.unterrainer.commons.httpserver.scripts.jsons.TestJson;
import info.unterrainer.commons.jreutils.ShutdownHook;
import info.unterrainer.commons.rdbutils.RdbUtils;
import info.unterrainer.commons.rdbutils.Transactions;
import info.unterrainer.commons.serialization.JsonMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LocalTestServer {

	private static JsonMapper mapper = JsonMapper.create();
	private static EntityManagerFactory emf;

	public static void main(final String[] args) throws Exception {

		try {
			emf = RdbUtils.createAutoclosingEntityManagerFactory(LocalTestServer.class, "test");
			Transactions.withNewTransaction(emf, em -> {
				em.persist(TestJpa.builder().message("server has been started.").build());
			});
			ShutdownHook.register(() -> {
				Transactions.withNewTransaction(emf, em -> {
					em.persist(TestJpa.builder().message("server has been stopped.").build());
				});
			});
			startServer();
		} catch (Exception e) {
			log.error("uncaught exception", e);
		}
	}

	private static void startServer() {
		HttpServer server = HttpServer.builder().applicationName("local-test-server").build();

		server.get("/status", new StatusHandler(mapper));
		server.handlerGroupFor(TestJpa.class, TestJson.class).path("/test").endpoints(Endpoint.ALL)
				.dao(new JpqlDao<>(emf, TestJpa.class)).jsonMapper(mapper).add();
		server.start();
	}

}
