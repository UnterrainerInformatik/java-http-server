package info.unterrainer.commons.httpserver.daos;

import java.util.List;
import java.util.function.Function;

import javax.persistence.EntityManager;

import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;
import info.unterrainer.commons.rdbutils.enums.AsyncState;

public interface BasicAsyncDao<P extends BasicAsyncJpa> extends BasicDao<P> {

	P getLastWith(final AsyncState... states);

	List<P> getLastNWith(final Long count, final AsyncState... states);

	P getNextWith(final AsyncState... states);

	List<P> getNextNWith(final Long count, final AsyncState... states);

	P lockedGetNextWith(final AsyncState stateToSetTo, final AsyncState... states);

	List<P> lockedGetNextNWith(final Long count, final AsyncState stateToSetTo, final AsyncState... states);

	P setStateTo(final AsyncState stateToSetTo, Long id);

	P setStateTo(final AsyncState stateToSetTo, Long id, final Function<P, P> additionalTransformations);

	P getLastWith(final EntityManager em, final AsyncState... states);

	List<P> getLastNWith(final EntityManager em, final Long count, final AsyncState... states);

	P getNextWith(final EntityManager em, final AsyncState... states);

	List<P> getNextNWith(final EntityManager em, final Long count, final AsyncState... states);

	P lockedGetNextWith(final EntityManager em, final AsyncState stateToSetTo, final AsyncState... states);

	List<P> lockedGetNextNWith(final EntityManager em, final Long count, final AsyncState stateToSetTo,
			final AsyncState... states);

	P setStateTo(final EntityManager em, final AsyncState stateToSetTo, Long id);

	P setStateTo(final EntityManager em, final AsyncState stateToSetTo, Long id,
			final Function<P, P> additionalTransformations);
}
