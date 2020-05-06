package info.unterrainer.commons.httpserver.extensions;

import java.util.function.Consumer;

import info.unterrainer.commons.httpserver.enums.Concurrency;
import info.unterrainer.commons.httpserver.enums.HttpMethod;
import info.unterrainer.commons.httpserver.enums.Position;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(fluent = true)
@Builder
public class ExtensionHandler<P extends BasicJpa, J extends BasicJson> {

	@Builder.Default
	private Concurrency concurrency = Concurrency.SYNC;
	@Builder.Default
	private HttpMethod method = HttpMethod.POST;
	@Builder.Default
	private Position position = Position.BEFORE;

	private Consumer<? extends ExtensionContext<P, J>> delegate;
}
