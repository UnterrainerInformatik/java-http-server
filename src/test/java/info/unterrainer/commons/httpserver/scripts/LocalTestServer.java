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
		server.handlerGroupFor(TestJpa.class, TestJson.class)
				.path("/test")
				.endpoints(Endpoint.ALL)
				.dao(new JpqlDao<>(emf, TestJpa.class))
				.jsonMapper(mapper)
				.extension()
				.preInsertSync((ctx, receivedJson, resultJpa) -> {
					log.info("before insert");
					return resultJpa;
				})
				.extension()
				.preDeleteSync((ctx, receivedId) -> {
					log.info("before delete id:[{}]", receivedId);
					return receivedId;
				})
				.extension()
				.preModifySync((ctx, receivedId, receivedJson, readJpa, resultJpa) -> {
					log.info("before modify");
					return resultJpa;
				})
				.extension()
				.postGetSingleSync((ctx, receivedId, readJpa, response) -> {
					log.info("after get-single");
					return response;
				})
				.extension()
				.postGetListSync((ctx, size, offset, readJpaList, responseList) -> {
					log.info("after get-list");
					return responseList;
				})
				.extension()
				.postDeleteSync((ctx, receivedId) -> {
					log.info("after delete");
					return true;
				})
				.extension()
				.postModifySync((ctx, receivedId, receivedJson, readJpa, mappedJpa, persistedJpa, response) -> {
					log.info("after modify");
					return response;
				})
				.extension()
				.postInsertSync((ctx, receivedJson, mappedJpa, createdJpa, response) -> {
					log.info("after insert");
					return response;
				})
				.add();
		server.start();
	}
}
