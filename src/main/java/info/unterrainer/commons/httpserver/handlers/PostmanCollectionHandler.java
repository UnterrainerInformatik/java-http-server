package info.unterrainer.commons.httpserver.handlers;

import info.unterrainer.commons.httpserver.enums.Attribute;
import info.unterrainer.commons.httpserver.enums.ResponseType;
import info.unterrainer.commons.httpserver.exceptions.NotFoundException;
import info.unterrainer.commons.jreutils.Resources;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostmanCollectionHandler implements Handler {

	private String collection;

	@Override
	public void handle(final Context ctx) throws Exception {
		if (collection == null)
			try {
				collection = Resources.readResource(PostmanCollectionHandler.class, "/postman_collection.json");
			} catch (Exception e) {
				throw new NotFoundException();
			}

		ctx.attribute(Attribute.RESPONSE_OBJECT, collection);
		ctx.attribute(Attribute.RESPONSE_TYPE, ResponseType.TEXT);
		ctx.attribute(Attribute.RESPONSE_STATUS, 200);
	}
}
