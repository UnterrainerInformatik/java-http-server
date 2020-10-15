package info.unterrainer.commons.httpserver.exceptions;

public class UnauthorizedException extends HttpException {

	private static final long serialVersionUID = -8429546406728629818L;

	public static final int HTTP_STATUS = 401;
	public static final String HTTP_TEXT = "Unauthorized";

	public UnauthorizedException(final String message, final Throwable cause) {
		super(HTTP_STATUS, HTTP_TEXT, message, cause);
	}

	public UnauthorizedException(final String message) {
		super(HTTP_STATUS, HTTP_TEXT, message);
	}

	public UnauthorizedException() {
		super(HTTP_STATUS, HTTP_TEXT);
	}
}
