package info.unterrainer.commons.httpserver;

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
import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddonBuilder<P extends BasicJpa, J extends BasicJson, E> {

	private final GenericHandlerGroupBuilder<P, J, E> builder;

	public GenericHandlerGroupBuilder<P, J, E> postDeleteAsync(final PostDeleteAsync delegate) {
		builder.extensions.postDeleteAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> postDeleteSync(final PostDeleteSync<E> delegate) {
		builder.extensions.postDeleteSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> postGetListAsync(final PostGetListAsync<P, J> delegate) {
		builder.extensions.postGetListAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> postGetListSync(final PostGetListSync<P, J, E> delegate) {
		builder.extensions.postGetListSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> postGetSingleAsync(final PostGetSingleAsync<P, J> delegate) {
		builder.extensions.postGetSingleAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> postGetSingleSync(final PostGetSingleSync<P, J, E> delegate) {
		builder.extensions.postGetSingleSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> postInsertAsync(final PostInsertAsync<P, J> delegate) {
		builder.extensions.postInsertAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> postInsertSync(final PostInsertSync<P, J, E> delegate) {
		builder.extensions.postInsertSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> postModifyAsync(final PostModifyAsync<P, J> delegate) {
		builder.extensions.postModifyAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> postModifySync(final PostModifySync<P, J, E> delegate) {
		builder.extensions.postModifySync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> preDeleteAsync(final PreDeleteAsync delegate) {
		builder.extensions.preDeleteAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> preDeleteSync(final PreDeleteSync<E> delegate) {
		builder.extensions.preDeleteSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> preInsertAsync(final PreInsertAsync<P, J> delegate) {
		builder.extensions.preInsertAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> preInsertSync(final PreInsertSync<P, J, E> delegate) {
		builder.extensions.preInsertSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> preModifyAsync(final PreModifyAsync<P, J> delegate) {
		builder.extensions.preModifyAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J, E> preModifySync(final PreModifySync<P, J, E> delegate) {
		builder.extensions.preModifySync().add(delegate);
		return builder;
	}
}
