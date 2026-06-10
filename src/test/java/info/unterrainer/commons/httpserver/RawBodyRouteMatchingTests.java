package info.unterrainer.commons.httpserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the raw-body opt-out route matching ({@code postRaw}/{@code putRaw}). These exercise
 * the path-template matching that decides whether {@code unzip} skips request-body buffering, without
 * needing a running server.
 */
public class RawBodyRouteMatchingTests {

	@Test
	public void normalizeStripsSurroundingSlashesAndWhitespace() {
		assertThat(HttpServer.normalizePath("/foo/bar/")).isEqualTo("foo/bar");
		assertThat(HttpServer.normalizePath("foo/bar")).isEqualTo("foo/bar");
		assertThat(HttpServer.normalizePath("  /foo/  ")).isEqualTo("foo");
		assertThat(HttpServer.normalizePath("/")).isEmpty();
		assertThat(HttpServer.normalizePath(null)).isEmpty();
	}

	@Test
	public void exactPathMatches() {
		assertThat(HttpServer.pathMatches("fileblobs/content", "fileblobs/content")).isTrue();
		assertThat(HttpServer.pathMatches("fileblobs/content", "fileblobs/other")).isFalse();
	}

	@Test
	public void pathParameterSegmentsMatchAnyValue() {
		assertThat(HttpServer.pathMatches("fileblobs/{id}/content", "fileblobs/123/content")).isTrue();
		assertThat(HttpServer.pathMatches("fileblobs/<id>/content", "fileblobs/a-b-c/content")).isTrue();
		assertThat(HttpServer.pathMatches("fileblobs/{id}/content", "fileblobs/123/other")).isFalse();
	}

	@Test
	public void segmentCountMustMatch() {
		assertThat(HttpServer.pathMatches("fileblobs/{id}/content", "fileblobs/123")).isFalse();
		assertThat(HttpServer.pathMatches("fileblobs/{id}", "fileblobs/123/content")).isFalse();
	}
}
