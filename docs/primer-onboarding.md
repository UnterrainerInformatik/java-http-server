# Primer: onboarding for `java-http-server`

> Read this first if you're a fresh Claude working in this repo. It gives you the project's
> purpose, the surrounding ecosystem, the conventions you must follow, the request pipeline,
> and how the downstream REST services consume this library. Pair it with `README.md` (usage
> examples) and `docs/primer-streaming-request-bodies.md` (the current task). Consider promoting
> the house rules below into a `CLAUDE.md` at the repo root.

## 1. What this repo is

`info.unterrainer.commons:http-server` — a thin **wrapper around [Javalin](https://javalin.io/)**
for building database-backed CRUD REST services with minimal boilerplate. You describe an
entity (JPA) + its DTO + a DAO, and the library generates the standard CRUD endpoints, with
**interceptors** and **sync extensions** that let the service hook into (and abort/alter) request
processing at any stage. It targets **MariaDB** via the sibling `rdb-utils` and uses **Keycloak**
for auth/roles.

Primary consumer: **`elite-server`** (the CMS backend at
`/mnt/data/source/elite-source/server/elite-server`) — your changes here ship to it. Other
UnterrainerInformatik services use it too.

## 2. The ecosystem (sibling repos on disk, all under `/mnt/data/source/JAVA/`)

- `java-parent-pom`, `java-parent-javalin-pom` — parent POMs (this repo's parent is
  `parent-javalin-pom`); dependency versions, Java level, plugins, release config live there.
- `java-rdb-utils` — JPA/MariaDB helpers, Liquibase startup, max-accuracy UTC timestamps,
  `BasicJpa`. This library's DAOs build on it.
- `java-jre-utils`, `java-serialization`, `java-rest-client` — utilities used across the stack.
- Consumers like `elite-source/server/elite-server` depend on the **published** artifact.

These are separate Maven artifacts released independently. You normally change **this** repo and
let consumers bump to the new version.

## 3. Versioning & release (important)

- **Version is derived from git tags**, not the `<version>` in `pom.xml` (which shows a
  placeholder like `0.1.0`/a stale number — ignore it). On push, `.github/workflows/bump-version.yml`
  (anothrNick/github-tag-action) bumps the tag; the build publishes to Maven Central.
- Releases happen from **`master`**. This checkout is currently on **`develop`**.
- After your change lands and a new version is tagged, the **consumer bumps its own pom manually**
  (e.g. `elite-server/pom.xml` → `info.unterrainer.commons:http-server` → new version, then
  `mvn compile`). You do not edit consumer poms from here.

## 4. House rules / conventions

- **No German artifacts anywhere in code** — folders, file/class/method/variable names, comments.
  Org-wide rule (the consumers enforce it too). If you find German, rename to English.
- **Tests:** JUnit + AssertJ under `src/test/java` (there's a test DB setup under
  `src/test/resources/db`). Match the existing style.
- **Build:** standard Maven (`mvn clean install`); Java level comes from the parent POM (the stack
  targets modern Java — verify via the parent, don't assume).
- **DTOs:** REST DTOs in consumers extend `BasicJson` (`id`, `createdOn`, `editedOn`) and live in the
  consumer's own `*-dtos` library — not here. This repo provides the generic machinery, not the
  concrete DTOs.

## 5. Architecture — key classes (package `info.unterrainer.commons.httpserver`)

- **`HttpServer`** — the entry point. Wraps Javalin, holds the route registry (`HandlerInstance`s),
  registers global `before`/`after` handlers, and starts the server. Route registration methods:
  `get/post/put/delete(path, handler, Role...)`. Generic CRUD via
  `handlerGroupFor(Jpa.class, Json.class, dao)` → `GenericHandlerGroupBuilder`.
- **`GenericHandlerGroup` / `GenericHandlerGroupBuilder`** — generate CRUD endpoints for an
  entity; `.path(...)`, `.endpoints(...)`, `.addRoleFor(...)`, and the **sync extension hooks**
  (`preInsertSync`, `postInsertSync`, `preModifySync`, `postModifySync`, `postGetSingleSync`,
  `postGetListSync`, `postDeleteSync`, …) that consumers use to alter/abort processing.
- **`interceptors/` and `extensions/`** — the hook machinery (and their `delegates/`).
- **`accessmanager/`** — Keycloak role enforcement (`RoleBuilder.named("...")`).
- **`daos/`** — MariaDB DAO layer (builds on `rdb-utils`).
- **`rql/` + `antlr/`** — an ANTLR-based query language for list filtering (the `filter`/RQL params).
- **`enums/Attribute`** — the `Context` attribute keys the pipeline reads/writes (see §6).
- **`HandlerUtils`** — request helpers (e.g. typed query-param accessors).

## 6. The request pipeline (you must understand this before touching `HttpServer`)

Per request, in order:

1. **Global `before` handlers** (registered in `HttpServer`, ~line 119+): set context attributes
   (`JAVALIN_SERVER`, `RESPONSE_TYPE=JSON`, `RESPONSE_CONTENT_TYPE=application/json`), then
   **`unzip(ctx)`** — which currently calls `ctx.body()` and stores `Attribute.REQUEST_BODY`
   (supporting gzipped request bodies). **This buffers every request body** — see the streaming
   primer; it's the thing being changed.
2. **Role gate** (access manager) for the matched route.
3. **The route handler** — either a generated CRUD handler or a custom `get/post/...` lambda.
   Handlers communicate the response by setting context attributes:
   `RESPONSE_OBJECT` (serialized to JSON by `render`), `RESPONSE_STATUS`, `RESPONSE_CONTENT_TYPE`,
   `RESPONSE_TYPE`.
4. **Global `after` handler `render(ctx)`** — if `RESPONSE_OBJECT != null`, serializes it (JSON) into
   the response body; then applies `RESPONSE_STATUS`/`RESPONSE_CONTENT_TYPE`. If `RESPONSE_OBJECT` is
   null it leaves the body alone (so handlers that wrote their own body/stream are not clobbered).

Multi-tenancy: the tenant is resolved from the Keycloak token and exposed via
`Attribute.USER_CLIENT_ATTRIBUTE_TENANT_VALUE`; DAOs scope queries by it.

## 7. How consumers use it (so you can predict the blast radius)

Generated CRUD group (typical):
```java
server.handlerGroupFor(FooJpa.class, FooJson.class, fooDao)
      .path("foos")
      .endpoints(Endpoint.ALL)
      .addRoleFor(Endpoint.ALL, RoleBuilder.named("technician"))
      .extension().postInsertSync((ctx, em, json, mapped, created, resp) -> { ... resp; })
      .build();
```
Custom route writing its own response:
```java
server.get("foos/download", ctx -> {
    ctx.attribute(Attribute.RESPONSE_OBJECT, null);                 // tell render() not to write a body
    ctx.attribute(Attribute.RESPONSE_CONTENT_TYPE, "application/octet-stream");
    ctx.attribute(Attribute.RESPONSE_STATUS, 200);
    ctx.result(inputStream);                                        // stream straight out
}, RoleBuilder.named("technician"));
```
Several consumer handlers read `ctx.attribute(Attribute.REQUEST_BODY)` for plain requests — so any
change to `unzip` must keep that populated for existing routes (the streaming primer covers this).

## 8. Build / test / verify

- Compile & test: `mvn clean install` (resolves the parent POM + sibling commons artifacts).
- Add tests under `src/test/java/...` (JUnit + AssertJ). The repo already wires a test DB under
  `src/test/resources/db` for DAO/integration tests.
- For a change consumed by `elite-server`, the end-to-end loop is: tag a release here → bump the dep
  in `elite-server/pom.xml` → compile/run elite-server (it deploys to staging on push to its master).

## 9. Your current task

`docs/primer-streaming-request-bodies.md` — add a **per-route opt-out** so a route can read the raw,
unbuffered request `InputStream` (for streaming large uploads to object storage) **without** changing
`REQUEST_BODY` behaviour for any existing route. The downstream plan that depends on it is
`elite-server/ai/plan-streaming-blob-upload.md`.
