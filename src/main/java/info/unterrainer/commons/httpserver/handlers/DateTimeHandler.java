package info.unterrainer.commons.httpserver.handlers;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.jsons.MessageJson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DateTimeHandler implements Handler {

	@Override
	public void handle(final Context ctx) throws Exception {
		ctx.attribute(Attribute.RESPONSE_OBJECT, MessageJson.builder()
				.message(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT)).build());
		ctx.attribute(Attribute.RESPONSE_STATUS, 200);
	}
}
