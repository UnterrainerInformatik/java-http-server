package info.unterrainer.commons.httpserver.scripts.handlers;

import info.unterrainer.commons.httpserver.scripts.jsons.TestJson;
import info.unterrainer.commons.serialization.JsonMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusHandler implements Handler {

	private final JsonMapper mapper;

	@Override
	public void handle(final Context ctx) {
		TestJson status = new TestJson("My status is great!");
		ctx.result(mapper.toStringFrom(status));
		ctx.status(200);
	}
}
