package info.unterrainer.commons.httpserver.accessmanager;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
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
import info.unterrainer.commons.httpserver.exceptions.GatewayTimeoutException;
import info.unterrainer.commons.httpserver.exceptions.UnauthorizedException;
import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpAccessManager implements AccessManager {

	private String host;
	private String realm;
	private PublicKey publicKey = null;

	public HttpAccessManager(final String host, final String realm) {
		this.host = host;
		this.realm = realm;
		try {
			initPublicKey();
		} catch (Exception e) {
			// Exceptions will terminate a request later on, but should not terminate the
			// main-thread here.
		}
	}

	@Override
	public void manage(final Handler handler, final Context ctx, final Set<Role> permittedRoles) throws Exception {
		checkAccess(ctx, permittedRoles);
		handler.handle(ctx);
	}

	private void initPublicKey() {
		if (publicKey != null)
			return;

		if (!host.endsWith("/"))
			host += "/";

		if (!realm.startsWith("/"))
			realm = "/" + realm;

		String authUrl = host + "auth/realms" + realm;
		try {
			log.info("Getting public key from: [{}]", authUrl);
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(new URI(authUrl)).build();
			ObjectMapper objectMapper = new ObjectMapper();

			client.sendAsync(request, BodyHandlers.ofString()).thenApply(response -> {
				if (response.statusCode() >= 300) {
					log.error("HTTP status [{}] getting public key from keycloak instance [{}].", response.statusCode(),
							authUrl);
					throw new GatewayTimeoutException(String
							.format("The keycloak instance returned an error (status: %d).", response.statusCode()));
				}
				return response.body();
			}).thenAccept(body -> {
				if (body == null)
					return;
				try {
					publicKey = objectMapper.readValue(body, PublishedRealmRepresentation.class).getPublicKey();
				} catch (IOException e) {
					log.error("Error parsing answer from keycloak.");
					throw new UncheckedIOException(e);
				}
			}).join();
		} catch (URISyntaxException e) {
			log.error("The keycloak URL was illegal [{}].", authUrl);
			throw new IllegalStateException(e);
		}
	}

	private void checkAccess(final Context ctx, final Set<Role> permittedRoles) {

		if (permittedRoles.isEmpty() || permittedRoles.contains(DefaultRoles.PUBLIC))
			return;

		initPublicKey();
		String authorizationHeader = ctx.header(HttpHeader.AUTHORIZATION.asString());
		TokenVerifier<AccessToken> tokenVerifier = TokenVerifier.create(authorizationHeader, AccessToken.class);
		tokenVerifier.publicKey(publicKey);

		try {
			tokenVerifier.verifySignature();
		} catch (VerificationException e) {
			throw new UnauthorizedException();
		}

		try {
			tokenVerifier.verify();
		} catch (VerificationException e) {
			throw new ForbiddenException();
		}
	}
}
