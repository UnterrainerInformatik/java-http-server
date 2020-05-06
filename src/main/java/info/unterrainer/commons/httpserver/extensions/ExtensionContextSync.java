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
public class ExtensionContextSync<P extends BasicJpa, J extends BasicJson> extends ExtensionContext<P, J> {

	protected boolean executionIsStopped;

	@Builder
	public ExtensionContextSync(final Context ctx, P jpa, J json) {
		super(ctx, jpa, json);
	}

	public void stopExecution() {
		executionIsStopped = true;
	}
}
