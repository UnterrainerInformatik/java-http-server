package info.unterrainer.commons.httpserver.scripts;

import info.unterrainer.commons.httpserver.HttpServer;
import info.unterrainer.commons.httpserver.scripts.handlers.StatusHandler;
import info.unterrainer.commons.serialization.JsonMapper;

public class LocalTestServer {

	public static JsonMapper mapper = JsonMapper.create();

	public static void main(final String[] args) throws Exception {
		HttpServer server = HttpServer.builder().applicationName("local-test-server").build();

		server.get("/test/status", new StatusHandler(mapper));

		server.run();
	}
}
