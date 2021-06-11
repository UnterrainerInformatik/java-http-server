package info.unterrainer.commons.httpserver.extensions;

import io.javalin.http.Context;

public interface AsyncExtensionContextMapper {

	AsyncExtensionContext map(Context ctx, AsyncExtensionContext asyncCtx);
}
