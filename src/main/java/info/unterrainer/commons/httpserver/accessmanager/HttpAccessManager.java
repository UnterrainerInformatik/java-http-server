package info.unterrainer.commons.httpserver.accessmanager;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.PublicKey;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jetty.http.HttpHeader;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.PublishedRealmRepresentation;

import com.fasterxml.jackson.databind.ObjectMapper;

import info.unterrainer.commons.httpserver.HttpServer;
import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.exceptions.ForbiddenException;
import info.unterrainer.commons.httpserver.exceptions.GatewayTimeoutException;
import info.unterrainer.commons.httpserver.exceptions.UnauthorizedException;
import info.unterrainer.commons.httpserver.jsons.UserDataJson;
import io.javalin.core.security.AccessManager;
import io.javalin.core.security.Role;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpAccessManager implements AccessManager {

	private String host;
	private String realm;
	private String authUrl;
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

		authUrl = host + "auth/realms" + realm;
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
				if (body == null) {
					log.warn("Received empty body.");
					return;
				}
				try {
					publicKey = objectMapper.readValue(body, PublishedRealmRepresentation.class).getPublicKey();
					log.info("Public key received.");
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
		try {
			TokenVerifier<AccessToken> tokenVerifier = persistUserInfoInContext(ctx);

			if (permittedRoles.isEmpty() || permittedRoles.contains(DefaultRole.OPEN) && permittedRoles.size() == 1)
				return;

			if (tokenVerifier == null)
				throw new UnauthorizedException();

			initPublicKey();
			tokenVerifier.publicKey(publicKey);
			try {
				tokenVerifier.verifySignature();
			} catch (VerificationException e) {
				throw new UnauthorizedException(
						"Error verifying token from user with publicKey obtained from keycloak.", e);
			}

			try {
				tokenVerifier.verify();
				if (permittedRoles.contains(DefaultRole.AUTHENTICATED) && permittedRoles.size() == 1)
					return;
				if (hasPermittedRole(ctx, permittedRoles))
					return;
				throw new ForbiddenException();
			} catch (VerificationException e) {
				throw new ForbiddenException();
			}
		} catch (Exception e) {
			log.error("Error checking token.", e);
			throw e;
		}
	}

	private boolean hasPermittedRole(final Context ctx, final Set<Role> permittedRoles) {
		Set<String> clientRoles = ctx.attribute(Attribute.USER_CLIENT_ROLES);
		for (Role role : permittedRoles)
			if (role instanceof NamedRole)
				if (clientRoles.contains(((NamedRole) role).name))
					return true;
		return false;
	}

	private TokenVerifier<AccessToken> persistUserInfoInContext(final Context ctx) {
		String authorizationHeader = ctx.header(HttpHeader.AUTHORIZATION.asString());

		if (authorizationHeader == null || authorizationHeader.isBlank())
			return null;

		if (authorizationHeader.toLowerCase().startsWith("bearer "))
			authorizationHeader = authorizationHeader.substring(7);

		try {
			TokenVerifier<AccessToken> tokenVerifier = TokenVerifier.create(authorizationHeader, AccessToken.class);

			AccessToken token = tokenVerifier.getToken();
			String userName = token.getPreferredUsername();
			if (userName == null)
				userName = "unknown";
			ctx.attribute(Attribute.USER_NAME, userName);
			ctx.attribute(Attribute.USER_GIVEN_NAME, token.getGivenName());
			ctx.attribute(Attribute.USER_FAMILY_NAME, token.getFamilyName());
			ctx.attribute(Attribute.USER_CLIENT, token.getIssuedFor());
			ctx.attribute(Attribute.USER_EMAIL, token.getEmail());
			ctx.attribute(Attribute.USER_EMAIL_VERIFIED, token.getEmailVerified());
			ctx.attribute(Attribute.USER_REALM_ROLES, token.getRealmAccess().getRoles());

			String readTenants = (String) token.getOtherClaims().get("tenants_read");
			ctx.attribute(Attribute.USER_CLIENT_ATTRIBUTE_TENANTS_READ, readTenants);
			ctx.attribute(Attribute.USER_TENANTS_READ_SET, createTenantSetFrom(readTenants));
			ctx.attribute(Attribute.USER_TENANT_READ, getFirstTenantFrom(readTenants));

			String writeTenants = (String) token.getOtherClaims().get("tenants_write");
			ctx.attribute(Attribute.USER_CLIENT_ATTRIBUTE_TENANTS_WRITE, writeTenants);
			ctx.attribute(Attribute.USER_TENANTS_WRITE_SET, createTenantSetFrom(writeTenants));
			ctx.attribute(Attribute.USER_TENANT_WRITE, getFirstTenantFrom(writeTenants));

			Set<String> clientRoles = Set.of();
			String key = token.getIssuedFor();
			if (token.getResourceAccess().containsKey(key))
				clientRoles = token.getResourceAccess().get(key).getRoles();
			ctx.attribute(Attribute.USER_CLIENT_ROLES, clientRoles);

			UserAccessInterceptor userAccessInterceptor = ((HttpServer) ctx.attribute(Attribute.JAVALIN_SERVER))
					.getUserAccessInterceptor();
			if (userAccessInterceptor != null)
				userAccessInterceptor.accept(ctx, token,
						UserDataJson.builder()
								.userName(userName)
								.givenName(token.getGivenName())
								.client(token.getIssuedFor())
								.familyName(token.getFamilyName())
								.email(token.getEmail())
								.emailVerified(token.getEmailVerified())
								.realmRoles(token.getRealmAccess().getRoles())
								.readTenants(readTenants)
								.writeTenants(writeTenants)
								.clientRoles(clientRoles)
								.isActive(token.isActive())
								.isBearer(token.getType().equalsIgnoreCase("bearer"))
								.build());

			if (!token.isActive()) {
				setTokenRejectionReason(ctx, "Token is inactive.");
				return null;
			}
			if (!token.getType().equalsIgnoreCase("bearer")) {
				setTokenRejectionReason(ctx, "Token is no bearer-token.");
				return null;
			}
			// Disabled to enable getting token from side-channels like 'localhost'.
			/*
			 * if (!token.getIssuer().equalsIgnoreCase(authUrl)) {
			 * setTokenRejectionReason(ctx, "Token has wrong real-url."); return null; }
			 */
			return tokenVerifier;

		} catch (VerificationException e) {
			setTokenRejectionReason(ctx, "Token was checked and deemed invalid.");
			return null;
		}
	}

	private Object createTenantSetFrom(final String tenant) {
		Set<Long> tenantSet = new HashSet<>();
		if (tenant == null || tenant.isBlank())
			return tenantSet;

		String[] tenants = tenant.split(",");
		for (String t : tenants) {
			if (t.isBlank())
				continue;
			try {
				tenantSet.add(Long.parseLong(t.trim()));
			} catch (NumberFormatException e) {
				// NOOP
			}
		}
		return tenantSet;
	}

	private Long getFirstTenantFrom(final String tenant) {
		if (tenant == null || tenant.isBlank())
			return null;

		String[] tenants = tenant.split(",");
		for (String t : tenants) {
			if (t.isBlank())
				continue;
			try {
				return Long.parseLong(t.trim());
			} catch (NumberFormatException e) {
				// NOOP
			}
		}
		return null;
	}

	private void setTokenRejectionReason(final Context ctx, final String reason) {
		ctx.attribute(Attribute.KEYCLOAK_TOKEN_REJECTION_REASON, reason);
	}
}
