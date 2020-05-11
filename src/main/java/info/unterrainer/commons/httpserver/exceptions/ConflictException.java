package info.unterrainer.commons.httpserver.exceptions;

public class ConflictException extends HttpException {

	private static final long serialVersionUID = 8228895423782666330L;

	public static final int HTTP_STATUS = 409;
	public static final String HTTP_TEXT = "Conflict";

	public ConflictException(final String message, final Throwable cause) {
		super(HTTP_STATUS, HTTP_TEXT, message, cause);
	}

	public ConflictException(final String message) {
		super(HTTP_STATUS, HTTP_TEXT, message);
	}

	public ConflictException() {
		super(HTTP_STATUS, HTTP_TEXT);
	}
}
