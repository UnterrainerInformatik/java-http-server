package info.unterrainer.commons.httpserver.extensions;

import info.unterrainer.commons.httpserver.enums.Attribute;
import io.javalin.http.Context;

public class AsyncExtensionContextBaseMapper implements AsyncExtensionContextMapper {

	@Override
	public AsyncExtensionContext map(final Context ctx, final AsyncExtensionContext asyncCtx) {
		asyncCtx.addParameter(ctx, Attribute.USER_CLIENT_ATTRIBUTE_TENANTS_READ);
		asyncCtx.addParameter(ctx, Attribute.USER_CLIENT_ATTRIBUTE_TENANTS_WRITE);
		asyncCtx.addParameter(ctx, Attribute.USER_TENANTS_READ_SET);
		asyncCtx.addParameter(ctx, Attribute.USER_TENANTS_WRITE_SET);
		asyncCtx.addParameter(ctx, Attribute.JAVALIN_SERVER);
		asyncCtx.addParameter(ctx, Attribute.RESPONSE_OBJECT);
		asyncCtx.addParameter(ctx, Attribute.RESPONSE_STATUS);
		asyncCtx.addParameter(ctx, Attribute.RESPONSE_TYPE);
		asyncCtx.addParameter(ctx, Attribute.USER_NAME);
		asyncCtx.addParameter(ctx, Attribute.USER_GIVEN_NAME);
		asyncCtx.addParameter(ctx, Attribute.USER_FAMILY_NAME);
		asyncCtx.addParameter(ctx, Attribute.USER_EMAIL);
		asyncCtx.addParameter(ctx, Attribute.USER_EMAIL_VERIFIED);
		asyncCtx.addParameter(ctx, Attribute.USER_CLIENT);
		asyncCtx.addParameter(ctx, Attribute.USER_CLIENT_ROLES);
		asyncCtx.addParameter(ctx, Attribute.USER_REALM_ROLES);
		asyncCtx.addParameter(ctx, Attribute.KEYCLOAK_TOKEN_REJECTION_REASON);
		return asyncCtx;
	}

}
