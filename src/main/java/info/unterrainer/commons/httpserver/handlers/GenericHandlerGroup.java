package info.unterrainer.commons.httpserver.handlers;

public class GenericHandlerGroup {

	private GenericHandlerGroup() {
	}

	GenericHandlerGroup(final int nr) {

	}

	public static GenericHandlerGroupBuilder builder() {
		return new GenericHandlerGroupBuilder();
	}
}
