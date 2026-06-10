# Primer: opt-out of request-body buffering (enable streaming uploads)

> Handoff for whoever works on **`java-http-server`** next. A downstream consumer
> (`elite-server`) needs to accept **multi-GB file uploads and stream them straight to
> object storage (MinIO)** without first buffering the whole body in memory. The framework
> currently makes that impossible. This primer explains why, the hard constraint that any
> fix must respect, and a recommended design. **No code has been changed yet.**

---

## 1. Why this is needed

`elite-server` stores file blobs in MinIO (S3). The **download** side already streams
(`GET /fileblobs/download` pipes `BlobStore.get(...)` straight to the response, byte-exact).
The **upload** side does not: the only ingress is a JSON `POST /fileblobs` with the bytes
base64-encoded in a field, which:

- inflates payloads ~33 % and forces a full JSON parse,
- is whole-body-buffered (see below), so it can't handle files larger than memory / the
  proxy body cap (a 110 MiB upload already 502s at the ingress).

To upload large files we want a route that reads the raw request **`InputStream`** and streams
it to `BlobStore.put(ref, in, size)` (the MinIO SDK does multipart from a stream — it handles
>20 GB). That is only possible if the framework does **not** read the body before the handler runs.

## 2. Current behaviour (the blocker)

`HttpServer` registers a **global** `before` handler that buffers **every** request body:

```java
// HttpServer.java
javalin.before(this::unzip);                       // line ~124  — runs for ALL routes

private void unzip(final Context ctx) throws IOException {   // line ~235
    String body = ctx.body();                      // <-- reads the ENTIRE body into memory (String)
    if (body == null) return;
    if (ctx.header("Content-Encoding") == "gzip") {
        BufferedSource bs = Okio.buffer(Okio.source(ctx.bodyAsInputStream()));
        GzipSource gzipSource = new GzipSource(bs);
        body = Okio.buffer(gzipSource).readUtf8();
    }
    ctx.bodyAsInputStream().reset();
    ctx.attribute(Attribute.REQUEST_BODY, body);   // <-- consumers read this
}
```

Because this runs for every route and calls `ctx.body()`, the body is fully materialised
(and UTF-8-decoded — meaningless for binary) before any handler executes. Routes registered
via `server.get/post/put/delete(...)` all pass through it; there is **no per-route bypass**.

Side note (also worth fixing): even routes that never need `REQUEST_BODY` pay this cost on
every request, and a `== "gzip"` reference-equality comparison on the header looks like a bug
(should be `.equalsIgnoreCase`/`"gzip".equals(...)`).

## 3. The hard constraint

You **cannot** simply stop populating `REQUEST_BODY`, nor gate it solely on `Content-Encoding: gzip`.
Multiple downstream handlers read `Attribute.REQUEST_BODY` on ordinary (non-gzip) requests, e.g. in
`elite-server`:

- `handlers/OpcUaSubscriptionHandler.java` (3×)
- `handlers/LocalizationHandler.java`
- `sse/transports/TransportHandler.java` (2×)
- `groupbuilders/OpcUaGroupBuilder.java` (2×)
- `groupbuilders/LoggerGroupBuilder.java` (2×)

Any change must keep `REQUEST_BODY` populated **exactly as today for every existing route**.
The streaming behaviour must be **opt-in per route**.

## 4. Recommended design — explicit per-route raw-body opt-out

Let a route declare that it wants the raw, unbuffered body; `unzip` skips exactly those routes.

1. **Mark raw-body routes.** Add raw variants (or a flag on the existing registration). Suggested
   minimal surface — new methods that register the route and remember it as raw:
   ```java
   public HttpServer postRaw(String path, Handler handler, Role... roles);   // + putRaw if needed
   ```
   Internally record `(HandlerType, normalisedPath)` in a `Set<String> rawBodyRoutes` on the server
   (mirror how `handlerInstances` is already collected/registered in `start()`).

2. **Skip in `unzip`.** First line of `unzip`, return early before touching the body:
   ```java
   private void unzip(final Context ctx) throws IOException {
       if (isRawBodyRoute(ctx.method(), ctx.path())) return;   // never read the body for these
       String body = ctx.body();
       ... // unchanged for everyone else; REQUEST_BODY still set
   }
   ```
   Match on the route's **registered** path template, not the raw URL, so path params still match.
   (If matching the template at `before`-time is awkward, an acceptable alternative is to skip when
   `Content-Type: application/octet-stream` **and** the route opted in — but prefer explicit route
   identity over content-type sniffing.)

3. **Handler reads the stream directly.** In a raw route the handler uses the servlet input stream
   (`ctx.req().getInputStream()` / `ctx.bodyAsInputStream()`) and must **not** call `ctx.body()` /
   `ctx.bodyAsBytes()` (those would buffer). Document this in the method's Javadoc.

Why explicit opt-out over "make `unzip` lazy globally": the lazy approach (only read on gzip)
would stop populating `REQUEST_BODY` for non-gzip requests and break the consumers in §3 unless
they're all migrated to `ctx.body()` first — a larger, breaking change. Opt-in is surgical and
zero-risk for existing routes.

## 5. Gotchas to check while implementing

- **Javalin 3.13.4 body cache / `maxRequestSize`.** Javalin caches the body up to a configured
  `maxRequestSize` when `ctx.body()`/`bodyAsBytes()` is called; reading `ctx.req().getInputStream()`
  directly avoids that cache. Confirm the raw route truly streams (test with a body larger than the
  cache limit) and that nothing upstream calls `ctx.body()` first for that route.
- **`render` after-handler.** The global `render(...)` only writes a body when `RESPONSE_OBJECT` is
  non-null, so it won't clobber a streamed response — but the streaming **upload** handler returns a
  small JSON/empty result, so set `RESPONSE_OBJECT`/status as usual; nothing special needed there.
- **Don't `reset()` a stream you didn't read.** The current `ctx.bodyAsInputStream().reset()` must
  not run on the raw path.
- **Auth/roles unchanged.** Raw routes still go through the access-manager role gate exactly like
  `get/post/...`; only the body-buffering `before` is skipped.

## 6. Release & how the consumer picks it up

Per the framework versioning convention, the version derives from **git tags via GitHub Actions** on
`master` (this repo is currently on `develop`). After landing the change:

1. Merge to `master`; the Action publishes a new tagged version.
2. In `elite-server/pom.xml`, bump the `info.unterrainer.commons:http-server` dependency to that
   version and `mvn compile` to confirm resolution.

## 7. Verification (framework side)

Add a test (JUnit + AssertJ, matching this repo's style) proving:

- A **raw** route receives the **unbuffered** stream: POST a body larger than the body cache, have the
  handler count bytes off `ctx.req().getInputStream()`, assert the full count arrives and
  `Attribute.REQUEST_BODY` is **null** for that route.
- A **normal** route is **unchanged**: `REQUEST_BODY` is still populated for plain and `gzip`-encoded
  requests.

## 8. What happens downstream once this ships (context, not your work)

`elite-server` will add a streaming upload endpoint (proposed: `POST /fileblobs` creates the metadata
row → returns id; `PUT /fileblobs/{id}/content` is a **raw** octet-stream route that streams
`ctx.req().getInputStream()` into `BlobStore.put(...)`, honouring the `MIGRATING` write-block and the
processing-size threshold), then a frontend primer will switch the UI to streaming up/download.
That work is **blocked on this framework change**; see `elite-server/ai/plan-streaming-blob-upload.md`.
