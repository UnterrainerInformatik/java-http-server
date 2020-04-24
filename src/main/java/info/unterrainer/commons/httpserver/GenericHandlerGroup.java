package info.unterrainer.commons.httpserver;

import info.unterrainer.commons.serialization.JsonMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFactory;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class GenericHandlerGroup<P, J> {

	private Class<P> jpaType;
	private Class<J> jsonType;
	private JsonMapper jsonMapper;
	private MapperFactory orikaFactory;
	private String path;
}
