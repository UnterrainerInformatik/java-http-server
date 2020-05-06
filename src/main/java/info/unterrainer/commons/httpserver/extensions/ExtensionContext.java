package info.unterrainer.commons.httpserver.extensions;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(fluent = true)
public abstract class ExtensionContext<P extends BasicJpa, J extends BasicJson> {

	protected Context ctx;
	protected P jpa;
	protected J json;
}
