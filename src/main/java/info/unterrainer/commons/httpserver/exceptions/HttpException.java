package info.unterrainer.commons.httpserver.exceptions;

import lombok.Getter;

public class HttpException extends RuntimeException {

	private static final long serialVersionUID = 661400825771800217L;
	@Getter
	private String httpText;
	@Getter
	private int httpStatus;

	public HttpException(final int httpStatus, final String httpText, final String message, final Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
		this.httpText = httpText;
	}

	public HttpException(final int httpStatus, final String httpText, final String message) {
		super(message);
		this.httpStatus = httpStatus;
		this.httpText = httpText;
	}

	public HttpException(final int httpStatus, final String httpText) {
		super();
		this.httpStatus = httpStatus;
		this.httpText = httpText;
	}
}
