package info.unterrainer.commons.httpserver.extensions;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class ExtensionContextAsyc<P extends BasicJpa, J extends BasicJson> extends ExtensionContext<P, J> {

	@Builder
	public ExtensionContextAsyc(final Context ctx, final P jpa, final J json) {
		super(ctx, jpa, json);
	}
}
