package info.unterrainer.commons.httpserver.exceptions;

public class NotFoundException extends HttpException {

	private static final long serialVersionUID = -8841150099309136642L;

	private static final int HTTP_STATUS = 404;
	private static final String HTTP_TEXT = "Not Found";

	public NotFoundException(final String message, final Throwable cause) {
		super(HTTP_STATUS, HTTP_TEXT, message, cause);
	}

	public NotFoundException(final String message) {
		super(HTTP_STATUS, HTTP_TEXT, message);
	}

	public NotFoundException() {
		super(HTTP_STATUS, HTTP_TEXT);
	}
}
