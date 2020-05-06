package info.unterrainer.commons.httpserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

import info.unterrainer.commons.httpserver.enums.Concurrency;
import info.unterrainer.commons.httpserver.enums.HttpMethod;
import info.unterrainer.commons.httpserver.enums.Position;
import info.unterrainer.commons.httpserver.extensions.ExtensionContextAsyc;
import info.unterrainer.commons.httpserver.extensions.ExtensionContextSync;
import info.unterrainer.commons.httpserver.extensions.ExtensionHandler;
import info.unterrainer.commons.httpserver.jsons.ListJson;
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import io.javalin.http.Context;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Accessors(fluent = true)
public class GenericHandlerAddons<P extends BasicJpa, J extends BasicJson> {

	private final List<ExtensionHandler<P, J>> beforeGetSingleHandlers = new ArrayList<>();
	private final List<ExtensionHandler<P, J>> afterGetSingleHandlers = new ArrayList<>();
	private final List<ExtensionHandler<P, J>> beforeGetListHandlers = new ArrayList<>();
	private final List<ExtensionHandler<P, J>> afterGetListHandlers = new ArrayList<>();
	private final List<ExtensionHandler<P, J>> beforeInsertHandlers = new ArrayList<>();
	private final List<ExtensionHandler<P, J>> afterInsertHandlers = new ArrayList<>();
	private final List<ExtensionHandler<P, J>> beforeUpdateHandlers = new ArrayList<>();
	private final List<ExtensionHandler<P, J>> afterUpdateHandlers = new ArrayList<>();
	private final List<ExtensionHandler<P, J>> beforeDeleteHandlers = new ArrayList<>();
	private final List<ExtensionHandler<P, J>> afterDeleteHandlers = new ArrayList<>();

	@SafeVarargs
	public final void add(final ExtensionHandler<P, J>... addonHandlers) {
		for (ExtensionHandler<P, J> h : addonHandlers)
			switch (h.method()) {
			case GET_SINGLE:
				if (h.position() == Position.BEFORE)
					beforeGetSingleHandlers().add(h);
				else
					afterGetSingleHandlers().add(h);
				break;
			case GET_LIST:
				if (h.position() == Position.BEFORE)
					beforeGetListHandlers().add(h);
				else
					afterGetListHandlers().add(h);
				break;
			case POST:
				if (h.position() == Position.BEFORE)
					beforeInsertHandlers().add(h);
				else
					afterInsertHandlers().add(h);
				break;
			case PUT:
				if (h.position() == Position.BEFORE)
					beforeUpdateHandlers().add(h);
				else
					afterUpdateHandlers().add(h);
				break;
			case DELETE:
				if (h.position() == Position.BEFORE)
					beforeDeleteHandlers().add(h);
				else
					afterDeleteHandlers().add(h);
				break;
			}
	}

	public final List<ExtensionHandler<P, J>> get(final Position position, final HttpMethod method) {
		switch (method) {
		case GET_SINGLE:
			if (position == Position.BEFORE)
				return beforeGetSingleHandlers();
			else
				return afterGetSingleHandlers();
		case GET_LIST:
			if (position == Position.BEFORE)
				return beforeGetListHandlers();
			else
				return afterGetListHandlers();
		case POST:
			if (position == Position.BEFORE)
				return beforeInsertHandlers();
			else
				return afterInsertHandlers();
		case PUT:
			if (position == Position.BEFORE)
				return beforeUpdateHandlers();
			else
				return afterUpdateHandlers();
		case DELETE:
			if (position == Position.BEFORE)
				return beforeDeleteHandlers();
			else
				return afterDeleteHandlers();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public boolean runHandlers(final Context ctx, final Position position, final HttpMethod method,
			final ExecutorService executorService, final P jpa, final J json, final ListJson<J> list) {
		for (ExtensionHandler<P, J> handler : get(position, method))
			if (handler.concurrency() == Concurrency.ASYNC)
				executorService.execute(() -> {
					ExtensionContextAsyc<P, J> asyncCtx = ExtensionContextAsyc.<P, J>builder().ctx(ctx).build();
					((Consumer<ExtensionContextAsyc<P, J>>) handler.delegate()).accept(asyncCtx);
				});
			else {
				ExtensionContextSync<P, J> syncCtx = ExtensionContextSync.<P, J>builder().ctx(ctx).build();
				((Consumer<ExtensionContextSync<P, J>>) handler.delegate()).accept(syncCtx);
				if (syncCtx.executionIsStopped())
					return false;
			}
		return true;
	}
}
