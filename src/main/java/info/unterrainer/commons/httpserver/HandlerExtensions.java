package info.unterrainer.commons.httpserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import info.unterrainer.commons.httpserver.exceptions.GracefulCancelationException;
import info.unterrainer.commons.httpserver.extensions.delegates.PostDeleteAsync;
import info.unterrainer.commons.httpserver.extensions.delegates.PostDeleteSync;
import info.unterrainer.commons.httpserver.extensions.delegates.PostGetListAsync;
import info.unterrainer.commons.httpserver.extensions.delegates.PostGetListSync;
import info.unterrainer.commons.httpserver.extensions.delegates.PostGetSingleAsync;
import info.unterrainer.commons.httpserver.extensions.delegates.PostGetSingleSync;
import info.unterrainer.commons.httpserver.extensions.delegates.PostInsertAsync;
import info.unterrainer.commons.httpserver.extensions.delegates.PostInsertSync;
import info.unterrainer.commons.httpserver.extensions.delegates.PostModifyAsync;
import info.unterrainer.commons.httpserver.extensions.delegates.PostModifySync;
import info.unterrainer.commons.httpserver.extensions.delegates.PreDeleteAsync;
import info.unterrainer.commons.httpserver.extensions.delegates.PreDeleteSync;
import info.unterrainer.commons.httpserver.extensions.delegates.PreInsertAsync;
import info.unterrainer.commons.httpserver.extensions.delegates.PreInsertSync;
import info.unterrainer.commons.httpserver.extensions.delegates.PreModifyAsync;
import info.unterrainer.commons.httpserver.extensions.delegates.PreModifySync;
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
public class HandlerExtensions<P extends BasicJpa, J extends BasicJson> {

	private final List<PostDeleteAsync> postDeleteAsync = new ArrayList<>();
	private final List<PostDeleteSync> postDeleteSync = new ArrayList<>();
	private final List<PostGetListAsync<P, J>> postGetListAsync = new ArrayList<>();
	private final List<PostGetListSync<P, J>> postGetListSync = new ArrayList<>();
	private final List<PostGetSingleAsync<P, J>> postGetSingleAsync = new ArrayList<>();
	private final List<PostGetSingleSync<P, J>> postGetSingleSync = new ArrayList<>();
	private final List<PostInsertAsync<P, J>> postInsertAsync = new ArrayList<>();
	private final List<PostInsertSync<P, J>> postInsertSync = new ArrayList<>();
	private final List<PostModifyAsync<P, J>> postModifyAsync = new ArrayList<>();
	private final List<PostModifySync<P, J>> postModifySync = new ArrayList<>();
	private final List<PreDeleteAsync> preDeleteAsync = new ArrayList<>();
	private final List<PreDeleteSync> preDeleteSync = new ArrayList<>();
	private final List<PreInsertAsync<P, J>> preInsertAsync = new ArrayList<>();
	private final List<PreInsertSync<P, J>> preInsertSync = new ArrayList<>();
	private final List<PreModifyAsync<P, J>> preModifyAsync = new ArrayList<>();
	private final List<PreModifySync<P, J>> preModifySync = new ArrayList<>();

	public void runPostGetSingle(final Context ctx, final Long receivedId, final P readJpa, final J response,
			final ExecutorService executorService) {
		for (PostGetSingleAsync<P, J> h : postGetSingleAsync())
			executorService.execute(() -> h.handle(receivedId, readJpa, response));

		J result = response;
		for (PostGetSingleSync<P, J> h : postGetSingleSync()) {
			result = h.handle(ctx, receivedId, readJpa, result);
			if (result == null)
				throw new GracefulCancelationException();
		}
	}

	public void runPostGetList(final Context ctx, final Long size, final Long offset, final ListJson<P> readList,
			final ListJson<J> response, final ExecutorService executorService) {
		for (PostGetListAsync<P, J> h : postGetListAsync())
			executorService.execute(() -> h.handle(size, offset, readList, response));

		ListJson<J> result = response;
		for (PostGetListSync<P, J> h : postGetListSync()) {
			result = h.handle(ctx, size, offset, readList, result);
			if (result == null)
				throw new GracefulCancelationException();
		}
	}
}
