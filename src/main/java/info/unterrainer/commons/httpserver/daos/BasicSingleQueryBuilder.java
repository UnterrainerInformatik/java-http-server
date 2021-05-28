package info.unterrainer.commons.httpserver.daos;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BasicSingleQueryBuilder<P extends BasicJpa, T, R extends BasicSingleQueryBuilder<P, T, R>>
		extends BasicQueryEntityManagerBuilder<P, T, R> {

	@Getter
	protected final BasicJpqlDao<P> dao;
	protected final Long id;
}
