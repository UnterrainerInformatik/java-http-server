package info.unterrainer.commons.httpserver.exceptions;

public class GatewayTimeoutException extends HttpException {

	private static final long serialVersionUID = 6409374003218276906L;

	public static final int HTTP_STATUS = 504;
	public static final String HTTP_TEXT = "Gateway Timeout";

	public GatewayTimeoutException(final String message, final Throwable cause) {
		super(HTTP_STATUS, HTTP_TEXT, message, cause);
	}

	public GatewayTimeoutException(final String message) {
		super(HTTP_STATUS, HTTP_TEXT, message);
	}

	public GatewayTimeoutException() {
		super(HTTP_STATUS, HTTP_TEXT);
	}
}
