package info.unterrainer.commons.httpserver;

import java.util.Optional;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
@Getter
public class HttpServerConfiguration {

	private HttpServerConfiguration() {
	}

	private int port;
	private String host;

	public static HttpServerConfiguration read() {
		return read(null);
	}

	public static HttpServerConfiguration read(final String prefix) {
		String p = "";
		if (prefix != null)
			p = prefix;
		HttpServerConfiguration config = new HttpServerConfiguration();
		config.port = Integer.parseInt(Optional.ofNullable(System.getenv(p + "HTTP_PORT")).orElse("8080"));
		config.host = Optional.ofNullable(System.getenv(p + "HTTP_HOST")).orElse("0.0.0.0");
		return config;
	}
}