package info.unterrainer.commons.httpserver.handlers;

import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.jsons.MessageJson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppNameHandler implements Handler {

	private final String applicationName;
	private MessageJson message;

	@Override
	public void handle(final Context ctx) throws Exception {
		if (message == null)
			message = MessageJson.builder().message(applicationName).build();
		ctx.attribute(Attribute.RESPONSE_OBJECT, message);
		ctx.attribute(Attribute.RESPONSE_STATUS, 200);
	}
}
