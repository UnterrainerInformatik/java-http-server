package info.unterrainer.commons.httpserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import info.unterrainer.commons.httpserver.exceptions.GracefulCancelationException;
import info.unterrainer.commons.httpserver.extensions.AsyncExtensionContext;
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
public class HandlerExtensions<P extends BasicJpa, J extends BasicJson, E> {

	private final List<PostDeleteAsync> postDeleteAsync = new ArrayList<>();
	private final List<PostDeleteSync<E>> postDeleteSync = new ArrayList<>();
	private final List<PostGetListAsync<P, J>> postGetListAsync = new ArrayList<>();
	private final List<PostGetListSync<P, J, E>> postGetListSync = new ArrayList<>();
	private final List<PostGetSingleAsync<P, J>> postGetSingleAsync = new ArrayList<>();
	private final List<PostGetSingleSync<P, J, E>> postGetSingleSync = new ArrayList<>();
	private final List<PostInsertAsync<P, J>> postInsertAsync = new ArrayList<>();
	private final List<PostInsertSync<P, J, E>> postInsertSync = new ArrayList<>();
	private final List<PostModifyAsync<P, J>> postModifyAsync = new ArrayList<>();
	private final List<PostModifySync<P, J, E>> postModifySync = new ArrayList<>();
	private final List<PreDeleteAsync> preDeleteAsync = new ArrayList<>();
	private final List<PreDeleteSync<E>> preDeleteSync = new ArrayList<>();
	private final List<PreInsertAsync<P, J>> preInsertAsync = new ArrayList<>();
	private final List<PreInsertSync<P, J, E>> preInsertSync = new ArrayList<>();
	private final List<PreModifyAsync<P, J>> preModifyAsync = new ArrayList<>();
	private final List<PreModifySync<P, J, E>> preModifySync = new ArrayList<>();

	public J runPostGetSingle(final Context ctx, final AsyncExtensionContext asyncCtx, final E entityManager,
			final Long receivedId, final P readJpa, final J response, final ExecutorService executorService) {
		for (PostGetSingleAsync<P, J> h : postGetSingleAsync())
			executorService.execute(() -> h.handle(asyncCtx, receivedId, readJpa, response));

		J result = response;
		for (PostGetSingleSync<P, J, E> h : postGetSingleSync()) {
			result = h.handle(ctx, entityManager, receivedId, readJpa, result);
			if (result == null)
				throw new GracefulCancelationException();
		}
		return result;
	}

	public ListJson<J> runPostGetList(final Context ctx, final AsyncExtensionContext asyncCtx, final E entityManager,
			final Long size, final Long offset, final ListJson<P> readList, final ListJson<J> response,
			final ExecutorService executorService) {
		for (PostGetListAsync<P, J> h : postGetListAsync())
			executorService.execute(() -> h.handle(asyncCtx, size, offset, readList, response));

		ListJson<J> result = response;
		for (PostGetListSync<P, J, E> h : postGetListSync()) {
			result = h.handle(ctx, entityManager, size, offset, readList, result);
			if (result == null)
				throw new GracefulCancelationException();
		}
		return result;
	}

	public P runPreInsert(final Context ctx, final AsyncExtensionContext asyncCtx, final E entityManager,
			final J receivedJson, final P resultJpa, final ExecutorService executorService) {
		for (PreInsertAsync<P, J> h : preInsertAsync())
			executorService.execute(() -> h.handle(asyncCtx, receivedJson, resultJpa));

		P result = resultJpa;
		for (PreInsertSync<P, J, E> h : preInsertSync()) {
			result = h.handle(ctx, entityManager, receivedJson, resultJpa);
			if (result == null)
				throw new GracefulCancelationException();
		}
		return result;
	}

	public J runPostInsert(final Context ctx, final AsyncExtensionContext asyncCtx, final E entityManager,
			final J receivedJson, final P mappedJpa, final P createdJpa, final J response,
			final ExecutorService executorService) {
		for (PostInsertAsync<P, J> h : postInsertAsync())
			executorService.execute(() -> h.handle(asyncCtx, receivedJson, mappedJpa, createdJpa, response));

		J result = response;
		for (PostInsertSync<P, J, E> h : postInsertSync()) {
			result = h.handle(ctx, entityManager, receivedJson, mappedJpa, createdJpa, response);
			if (result == null)
				throw new GracefulCancelationException();
		}
		return result;
	}

	public P runPreModify(final Context ctx, final AsyncExtensionContext asyncCtx, final E entityManager,
			final Long receivedId, final J receivedJson, final P readJpa, final P resultJpa,
			final ExecutorService executorService) {
		for (PreModifyAsync<P, J> h : preModifyAsync())
			executorService.execute(() -> h.handle(asyncCtx, receivedId, receivedJson, readJpa, resultJpa));

		P result = resultJpa;
		for (PreModifySync<P, J, E> h : preModifySync()) {
			result = h.handle(ctx, entityManager, receivedId, receivedJson, readJpa, resultJpa);
			if (result == null)
				throw new GracefulCancelationException();
		}
		return result;
	}

	public J runPostModify(final Context ctx, final AsyncExtensionContext asyncCtx, final E entityManager,
			final Long receivedId, final J receivedJson, final P readJpa, final P mappedJpa, final P persistedJpa,
			final J response, final ExecutorService executorService) {
		for (PostModifyAsync<P, J> h : postModifyAsync())
			executorService.execute(
					() -> h.handle(asyncCtx, receivedId, receivedJson, readJpa, mappedJpa, persistedJpa, response));

		J result = response;
		for (PostModifySync<P, J, E> h : postModifySync()) {
			result = h.handle(ctx, entityManager, receivedId, receivedJson, readJpa, mappedJpa, persistedJpa, response);
			if (result == null)
				throw new GracefulCancelationException();
		}
		return result;
	}

	public Long runPreDelete(final Context ctx, final AsyncExtensionContext asyncCtx, final E entityManager,
			final Long receivedId, final ExecutorService executorService) {
		for (PreDeleteAsync h : preDeleteAsync())
			executorService.execute(() -> h.handle(asyncCtx, receivedId));

		Long result = receivedId;
		for (PreDeleteSync<E> h : preDeleteSync()) {
			result = h.handle(ctx, entityManager, receivedId);
			if (result == null)
				throw new GracefulCancelationException();
		}
		return result;
	}

	public void runPostDelete(final Context ctx, final AsyncExtensionContext asyncCtx, final E entityManager,
			final Long receivedId, final ExecutorService executorService) {
		for (PostDeleteAsync h : postDeleteAsync())
			executorService.execute(() -> h.handle(asyncCtx, receivedId));

		for (PostDeleteSync<E> h : postDeleteSync())
			if (!h.handle(ctx, entityManager, receivedId))
				throw new GracefulCancelationException();
	}
}
