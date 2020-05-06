package info.unterrainer.commons.httpserver.exceptions;

public class GracefulCancelationException extends HttpException {

	private static final long serialVersionUID = 6166605097940506962L;

	public static final int HTTP_STATUS = 200;
	public static final String HTTP_TEXT = "Operation Canceled Gracefully";

	public GracefulCancelationException(final String message, final Throwable cause) {
		super(HTTP_STATUS, HTTP_TEXT, message, cause);
	}

	public GracefulCancelationException(final String message) {
		super(HTTP_STATUS, HTTP_TEXT, message);
	}

	public GracefulCancelationException() {
		super(HTTP_STATUS, HTTP_TEXT);
	}
}
