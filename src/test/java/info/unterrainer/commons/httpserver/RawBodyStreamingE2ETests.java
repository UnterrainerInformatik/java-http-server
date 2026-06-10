package info.unterrainer.commons.httpserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.Test;

import info.unterrainer.commons.httpserver.accessmanager.RoleBuilder;
import info.unterrainer.commons.httpserver.enums.Attribute;

/**
 * End-to-end verification of the raw-body opt-out ({@code postRaw}) against a real, running server.
 * <p>
 * Needs no database and no Keycloak: both routes are registered with {@link RoleBuilder#open()}, which
 * short-circuits the access manager, so the server boots and serves with just an HTTP port. It binds the
 * configured {@code HTTP_PORT} (default 8080) on loopback. If that port is taken (e.g. another booted
 * {@code HttpServer} in the same suite), set {@code HTTP_PORT} to a free port before running.
 * <p>
 * Proves, per {@code docs/primer-streaming-request-bodies.md} §7:
 * <ul>
 * <li>a <b>raw</b> route receives the full, <b>unbuffered</b> stream for a body larger than Javalin's
 * body cache, and {@link Attribute#REQUEST_BODY} stays {@code null} for it;</li>
 * <li>a <b>normal</b> route is unchanged: {@code REQUEST_BODY} is still populated with the full body.</li>
 * </ul>
 */
public class RawBodyStreamingE2ETests {

	// Javalin 3.13's default maxRequestSize is 1 MB; go well past it so the raw path can only succeed by
	// truly streaming rather than relying on the cached body.
	private static final int BODY_SIZE = 3 * 1024 * 1024;

	private static final int PORT = Integer.parseInt(System.getProperty("HTTP_PORT",
			System.getenv().getOrDefault("HTTP_PORT", "8080")));

	// Observations recorded by the in-process handlers and asserted by the test.
	private static final AtomicLong rawBytesCounted = new AtomicLong(-1);
	private static final AtomicReference<String> rawRequestBody = new AtomicReference<>("__unset__");
	private static final AtomicReference<String> normalRequestBody = new AtomicReference<>("__unset__");

	@Test
	public void rawRouteStreamsUnbufferedAndNormalRouteStillBuffers() throws Exception {
		HttpServer server = HttpServer.builder().applicationName("raw-body-e2e-test").build();

		server.postRaw("upload/raw", ctx -> {
			long count = 0;
			try (InputStream in = ctx.req.getInputStream()) {
				byte[] buffer = new byte[64 * 1024];
				int read;
				while ((read = in.read(buffer)) != -1)
					count += read;
			}
			rawBytesCounted.set(count);
			rawRequestBody.set(ctx.attribute(Attribute.REQUEST_BODY));
			ctx.attribute(Attribute.RESPONSE_OBJECT, null);
			ctx.attribute(Attribute.RESPONSE_STATUS, 200);
			ctx.result("ok");
		}, RoleBuilder.open());

		server.post("upload/normal", ctx -> {
			normalRequestBody.set(ctx.attribute(Attribute.REQUEST_BODY));
			ctx.attribute(Attribute.RESPONSE_OBJECT, null);
			ctx.attribute(Attribute.RESPONSE_STATUS, 200);
			ctx.result("ok");
		}, RoleBuilder.open());

		server.start();

		byte[] payload = new byte[BODY_SIZE];
		for (int i = 0; i < payload.length; i++)
			payload[i] = (byte) (i % 251);

		HttpClient client = HttpClient.newHttpClient();

		HttpResponse<String> rawResponse = client.send(
				HttpRequest.newBuilder(URI.create("http://localhost:" + PORT + "/upload/raw"))
						.POST(BodyPublishers.ofByteArray(payload))
						.build(),
				BodyHandlers.ofString());

		HttpResponse<String> normalResponse = client.send(
				HttpRequest.newBuilder(URI.create("http://localhost:" + PORT + "/upload/normal"))
						.POST(BodyPublishers.ofString("hello-normal-route"))
						.build(),
				BodyHandlers.ofString());

		assertThat(rawResponse.statusCode()).isEqualTo(200);
		assertThat(normalResponse.statusCode()).isEqualTo(200);

		// Raw route: full body streamed off the InputStream, and unzip never populated REQUEST_BODY.
		assertThat(rawBytesCounted.get()).isEqualTo(BODY_SIZE);
		assertThat(rawRequestBody.get()).isNull();

		// Normal route: unchanged - REQUEST_BODY still holds the full body.
		assertThat(normalRequestBody.get()).isEqualTo("hello-normal-route");
	}
}
