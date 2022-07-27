package info.unterrainer.commons.httpserver.scripts.handlers;

import info.unterrainer.commons.httpserver.scripts.jsons.TestJson;
import info.unterrainer.commons.serialization.jsonmapper.JsonMapper;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusHandler implements Handler {

	private final JsonMapper mapper;

	@Override
	public void handle(final Context ctx) {
		TestJson status = TestJson.builder().message("My status is great!").build();
		ctx.result(mapper.toStringFrom(status));
		ctx.status(200);
	}
}
