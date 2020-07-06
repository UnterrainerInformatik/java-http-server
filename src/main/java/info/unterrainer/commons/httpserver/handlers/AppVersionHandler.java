package info.unterrainer.commons.httpserver.handlers;

import info.unterrainer.commons.httpserver.Information;
import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.jsons.AppVersionJson;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppVersionHandler implements Handler {

	private AppVersionJson message;

	@Override
	public void handle(final Context ctx) throws Exception {
		if (message == null)
			message = AppVersionJson.builder()
					.PomVersion(Information.PomVersion)
					.BuildTime(Information.BuildTime)
					.build();
		ctx.attribute(Attribute.RESPONSE_OBJECT, message);
		ctx.attribute(Attribute.RESPONSE_STATUS, 200);
	}
}
