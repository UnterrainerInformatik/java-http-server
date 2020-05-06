package info.unterrainer.commons.httpserver.exceptions;

public class BadRequestException extends HttpException {

	private static final long serialVersionUID = -8841150099309136642L;

	public static final int HTTP_STATUS = 400;
	public static final String HTTP_TEXT = "Bad Request";

	public BadRequestException(final String message, final Throwable cause) {
		super(HTTP_STATUS, HTTP_TEXT, message, cause);
	}

	public BadRequestException(final String message) {
		super(HTTP_STATUS, HTTP_TEXT, message);
	}

	public BadRequestException() {
		super(HTTP_STATUS, HTTP_TEXT);
	}
}
