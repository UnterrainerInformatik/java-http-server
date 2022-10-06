package info.unterrainer.commons.httpserver;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import info.unterrainer.commons.httpserver.accessmanager.RoleBuilder;
import info.unterrainer.commons.httpserver.daos.JpqlDao;
import info.unterrainer.commons.httpserver.enums.Endpoint;
import info.unterrainer.commons.httpserver.scripts.LocalTestServer;
import info.unterrainer.commons.httpserver.scripts.jpas.TestJpa;
import info.unterrainer.commons.httpserver.scripts.jsons.TestJson;
import info.unterrainer.commons.rdbutils.RdbUtils;
import info.unterrainer.commons.rdbutils.exceptions.RdbUtilException;
import info.unterrainer.commons.serialization.jsonmapper.JsonMapper;
import info.unterrainer.commons.serialization.objectmapper.ObjectMapper;
import jakarta.persistence.EntityManagerFactory;

public class HttpServerTests {

	@Test
	public void test() throws RdbUtilException {
		EntityManagerFactory emf;
		emf = RdbUtils.createAutoclosingEntityManagerFactory(LocalTestServer.class, "test");
		JsonMapper jsonMapper = JsonMapper.create();
		ObjectMapper objectMapper = new ObjectMapper();
		ThreadPoolExecutor executorService = new ThreadPoolExecutor(20, 20, 60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
		executorService.allowCoreThreadTimeOut(true);
		HttpServer server = HttpServer.builder()
				.applicationName("elite-server")
				.jsonMapper(jsonMapper)
				.objectMapper(objectMapper)
				.executorService(executorService)
				.appVersionFqns(new String[] { "at.elitezettl.server.eliteserver.Information",
						"info.unterrainer.commons.httpserver.Information",
						"at.elitezettl.commons.opcuabrowser.Information",
						"info.unterrainer.commons.crontabscheduler.Information",
						"info.unterrainer.commons.jreutils.Information",
						"info.unterrainer.commons.rdbutils.Information",
						"info.unterrainer.commons.serialization.Information" })
				.build();
		JpqlDao<TestJpa> basicDao = new JpqlDao<>(emf, TestJpa.class);
		server.handlerGroupFor(TestJpa.class, TestJson.class, basicDao)
				.path("tests")
				.endpoints(Endpoint.ALL)
				.addRoleFor(Endpoint.ALL, RoleBuilder.open())
				.add();
	}
}
