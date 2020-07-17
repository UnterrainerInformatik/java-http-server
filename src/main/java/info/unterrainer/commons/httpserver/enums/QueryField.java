package info.unterrainer.commons.httpserver.enums;

public class QueryField {

	public static final String ID = "ID";
	public static final String PAGINATION_SIZE = "size";
	public static final String PAGINATION_OFFSET = "offset";
	public static final String LIST_LINK = "%s?" + PAGINATION_OFFSET + "=%d&" + PAGINATION_SIZE + "=%d%s";
}
