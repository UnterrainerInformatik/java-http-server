package info.unterrainer.commons.httpserver.daos;

import javax.persistence.EntityManager;

import info.unterrainer.commons.rdbutils.entities.BasicAsyncJpa;
import info.unterrainer.commons.rdbutils.enums.AsyncState;

public interface BasicAsyncDao<P extends BasicAsyncJpa> extends BasicDao<P> {

	P lockedGetNextWith(final AsyncState stateToSetTo, final AsyncState... states);

	P setStateTo(final AsyncState stateToSetTo, Long id);

	P lockedGetNextWith(EntityManager em, final AsyncState stateToSetTo, final AsyncState... states);

	P setStateTo(EntityManager em, final AsyncState stateToSetTo, Long id);
}
