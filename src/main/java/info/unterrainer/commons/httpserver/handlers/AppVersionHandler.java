package info.unterrainer.commons.httpserver.handlers;

import java.util.ArrayList;
import java.util.List;

import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.jsons.AppVersionJson;
import info.unterrainer.commons.httpserver.jsons.VersionsJson;
import info.unterrainer.commons.jreutils.ForName;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AppVersionHandler implements Handler {

	private final static String NAME = "name";
	private final static String BUILD_TIME = "buildTime";
	private final static String POM_VERSION = "pomVersion";
	private final List<String> appVersionFqns;
	private VersionsJson message;

	@Override
	public void handle(final Context ctx) throws Exception {
		if (message == null)
			message = buildMessage();
		ctx.attribute(Attribute.RESPONSE_OBJECT, message);
		ctx.attribute(Attribute.RESPONSE_STATUS, 200);
	}

	private VersionsJson buildMessage() {
		List<AppVersionJson> versions = new ArrayList<>();
		for (String fqn : appVersionFqns) {
			Class<?> clazz = ForName.get(fqn);
			if (clazz == null)
				continue;

			String name;
			String buildTime;
			String pomVersion;
			try {
				name = (String) clazz.getField(NAME).get(null);
				buildTime = (String) clazz.getField(BUILD_TIME).get(null);
				pomVersion = (String) clazz.getField(POM_VERSION).get(null);

			} catch (IllegalAccessException | IllegalArgumentException | SecurityException | NoSuchFieldException e) {
				continue;
			}

			AppVersionJson av = AppVersionJson.builder().name(name).pomVersion(pomVersion).buildTime(buildTime).build();
			versions.add(av);
		}
		return VersionsJson.builder().Versions(versions).build();
	}
}
