package info.unterrainer.commons.httpserver.exceptions;

public class InternalServerErrorException extends HttpException {

	private static final long serialVersionUID = 3106441166922743283L;

	public static final int HTTP_STATUS = 500;
	public static final String HTTP_TEXT = "Internal Server Error";

	public InternalServerErrorException(final String message, final Throwable cause) {
		super(HTTP_STATUS, HTTP_TEXT, message, cause);
	}

	public InternalServerErrorException(final String message) {
		super(HTTP_STATUS, HTTP_TEXT, message);
	}

	public InternalServerErrorException() {
		super(HTTP_STATUS, HTTP_TEXT);
	}
}
