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
public class AddonBuilder<P extends BasicJpa, J extends BasicJson> {

	private final GenericHandlerGroupBuilder<P, J> builder;

	public GenericHandlerGroupBuilder<P, J> postDeleteAsync(final PostDeleteAsync delegate) {
		builder.extensions.postDeleteAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> postDeleteSync(final PostDeleteSync delegate) {
		builder.extensions.postDeleteSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> postGetListAsync(final PostGetListAsync<P, J> delegate) {
		builder.extensions.postGetListAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> postGetListSync(final PostGetListSync<P, J> delegate) {
		builder.extensions.postGetListSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> postGetSingleAsync(final PostGetSingleAsync<P, J> delegate) {
		builder.extensions.postGetSingleAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> postGetSingleSync(final PostGetSingleSync<P, J> delegate) {
		builder.extensions.postGetSingleSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> postInsertAsync(final PostInsertAsync<P, J> delegate) {
		builder.extensions.postInsertAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> postInsertSync(final PostInsertSync<P, J> delegate) {
		builder.extensions.postInsertSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> postModifyAsync(final PostModifyAsync<P, J> delegate) {
		builder.extensions.postModifyAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> postModifySync(final PostModifySync<P, J> delegate) {
		builder.extensions.postModifySync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> preDeleteAsync(final PreDeleteAsync delegate) {
		builder.extensions.preDeleteAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> preDeleteSync(final PreDeleteSync delegate) {
		builder.extensions.preDeleteSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> preInsertAsync(final PreInsertAsync<P, J> delegate) {
		builder.extensions.preInsertAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> preInsertSync(final PreInsertSync<P, J> delegate) {
		builder.extensions.preInsertSync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> preModifyAsync(final PreModifyAsync<P, J> delegate) {
		builder.extensions.preModifyAsync().add(delegate);
		return builder;
	}

	public GenericHandlerGroupBuilder<P, J> preModifySync(final PreModifySync<P, J> delegate) {
		builder.extensions.preModifySync().add(delegate);
		return builder;
	}
}
