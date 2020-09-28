package info.unterrainer.commons.httpserver.exceptions;

public class ForbiddenException extends HttpException {

	private static final long serialVersionUID = -9128226690837369251L;

	public static final int HTTP_STATUS = 403;
	public static final String HTTP_TEXT = "Forbidden";

	public ForbiddenException(final String message, final Throwable cause) {
		super(HTTP_STATUS, HTTP_TEXT, message, cause);
	}

	public ForbiddenException(final String message) {
		super(HTTP_STATUS, HTTP_TEXT, message);
	}

	public ForbiddenException() {
		super(HTTP_STATUS, HTTP_TEXT);
	}
}
