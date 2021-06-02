package info.unterrainer.commons.httpserver.scripts;

import javax.persistence.EntityManagerFactory;

import info.unterrainer.commons.httpserver.HttpServer;
import info.unterrainer.commons.httpserver.accessmanager.RoleBuilder;
import info.unterrainer.commons.httpserver.daos.JpqlDao;
import info.unterrainer.commons.httpserver.enums.Endpoint;
import info.unterrainer.commons.httpserver.scripts.handlers.StatusHandler;
import info.unterrainer.commons.httpserver.scripts.jpas.TestJpa;
import info.unterrainer.commons.httpserver.scripts.jpas.TestPermissionJpa;
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

		server.get("/status", new StatusHandler(mapper), RoleBuilder.authenticated());
		server.get("/status2", new StatusHandler(mapper), RoleBuilder.named("admin"));
		server.handlerGroupFor(TestJpa.class, TestJson.class, new JpqlDao<>(emf, TestJpa.class))
				.path("/test")
				.endpoints(Endpoint.ALL)
				.jsonMapper(mapper)
				.addRoleFor(Endpoint.ALL, RoleBuilder.authenticated())
				.addRoleFor(Endpoint.GET_SINGLE, RoleBuilder.named("user"))
				.extension()
				.preInsertSync((ctx, em, receivedJson, resultJpa) -> {
					log.info("before insert");
					return resultJpa;
				})
				.extension()
				.preDeleteSync((ctx, em, receivedId) -> {
					log.info("before delete id:[{}]", receivedId);
					return receivedId;
				})
				.extension()
				.preModifySync((ctx, em, receivedId, receivedJson, readJpa, resultJpa) -> {
					log.info("before modify");
					return resultJpa;
				})
				.extension()
				.postGetSingleSync((ctx, em, receivedId, readJpa, response) -> {
					log.info("after get-single");
					return response;
				})
				.extension()
				.postGetListSync((ctx, em, size, offset, readJpaList, responseList) -> {
					log.info("after get-list");
					return responseList;
				})
				.extension()
				.postDeleteSync((ctx, em, receivedId) -> {
					log.info("after delete");
					return true;
				})
				.extension()
				.postModifySync((ctx, em, receivedId, receivedJson, readJpa, mappedJpa, persistedJpa, response) -> {
					log.info("after modify");
					return response;
				})
				.extension()
				.postInsertSync((ctx, em, receivedJson, mappedJpa, createdJpa, response) -> {
					log.info("after insert");
					return response;
				})
				.add();

		server.handlerGroupFor(TestJpa.class, TestJson.class,
				new JpqlDao<>(emf, TestJpa.class, TestPermissionJpa.class))
				.path("/tenanttests")
				.endpoints(Endpoint.ALL)
				.jsonMapper(mapper)
				.addRoleFor(Endpoint.ALL, RoleBuilder.authenticated())
				.extension()
				.preInsertSync((ctx, em, receivedJson, resultJpa) -> {
					log.info("before insert");
					return resultJpa;
				})
				.extension()
				.preDeleteSync((ctx, em, receivedId) -> {
					log.info("before delete id:[{}]", receivedId);
					return receivedId;
				})
				.extension()
				.preModifySync((ctx, em, receivedId, receivedJson, readJpa, resultJpa) -> {
					log.info("before modify");
					return resultJpa;
				})
				.extension()
				.postGetSingleSync((ctx, em, receivedId, readJpa, response) -> {
					log.info("after get-single");
					return response;
				})
				.extension()
				.postGetListSync((ctx, em, size, offset, readJpaList, responseList) -> {
					log.info("after get-list");
					return responseList;
				})
				.extension()
				.postDeleteSync((ctx, em, receivedId) -> {
					log.info("after delete");
					return true;
				})
				.extension()
				.postModifySync((ctx, em, receivedId, receivedJson, readJpa, mappedJpa, persistedJpa, response) -> {
					log.info("after modify");
					return response;
				})
				.extension()
				.postInsertSync((ctx, em, receivedJson, mappedJpa, createdJpa, response) -> {
					log.info("after insert");
					return response;
				})
				.add();

		server.start();
	}
}
