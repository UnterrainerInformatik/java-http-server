package info.unterrainer.commons.httpserver.accessmanager;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.PublicKey;
import java.util.Set;

import org.eclipse.jetty.http.HttpHeader;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.PublishedRealmRepresentation;

import com.fasterxml.jackson.databind.ObjectMapper;

import info.unterrainer.commons.httpserver.exceptions.ForbiddenException;
import info.unterrainer.commons.httpserver.exceptions.UnauthorizedException;
import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class HttpAccessManager implements AccessManager {

	private PublicKey publicKey;

	public HttpAccessManager() {
		try {
			String authUrl = "http://localhost:8080" + "/auth/realms/" + "nexus";

			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(new URI(authUrl)).build();
			ObjectMapper objectMapper = new ObjectMapper();

			client.sendAsync(request, BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(body -> {
				try {
					publicKey = objectMapper.readValue(body, PublishedRealmRepresentation.class).getPublicKey();
				} catch (IOException e) {
					throw new UncheckedIOException(e);
				}
			}).join();
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}

	}

	@Override
	public void manage(Handler handler, Context ctx, Set<Role> permittedRoles) throws Exception {

		checkAccess(ctx, permittedRoles);

		handler.handle(ctx);
	}

	private void checkAccess(Context ctx, Set<Role> permittedRoles) {

		if (permittedRoles.isEmpty() || permittedRoles.contains(DefaultRoles.PUBLIC)) {
			return;
		}

		String authorizationHeader = ctx.header(HttpHeader.AUTHORIZATION.asString());
		TokenVerifier<AccessToken> accessToken = TokenVerifier.create(authorizationHeader, AccessToken.class);
		accessToken.publicKey(publicKey);

		try {
			accessToken.verifySignature();
		} catch (VerificationException e) {
			throw new UnauthorizedException();
		}

		try {
			accessToken.verify();
		} catch (VerificationException e) {
			throw new ForbiddenException();
		}
	}
}
