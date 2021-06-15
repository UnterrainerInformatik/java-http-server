package info.unterrainer.commons.httpserver.accessmanager;

import org.keycloak.representations.AccessToken;

import info.unterrainer.commons.httpserver.jsons.UserDataJson;
import io.javalin.http.Context;

@FunctionalInterface
public interface UserAccessInterceptor {

	void accept(Context ctx, AccessToken token, UserDataJson userDataJson);
}
