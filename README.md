![GitHub forks](https://img.shields.io/github/forks/UnterrainerInformatik/java-http-server?style=social) ![GitHub stars](https://img.shields.io/github/stars/UnterrainerInformatik/java-http-server?style=social) ![GitHub repo size](https://img.shields.io/github/repo-size/UnterrainerInformatik/java-http-server) [![GitHub issues](https://img.shields.io/github/issues/UnterrainerInformatik/java-http-server)](https://github.com/UnterrainerInformatik/java-http-server/issues)

[![license](https://img.shields.io/github/license/unterrainerinformatik/FiniteStateMachine.svg?maxAge=2592000)](http://unlicense.org) [![Travis-build](https://travis-ci.org/UnterrainerInformatik/java-http-server.svg?branch=master)](https://travis-ci.org/github/UnterrainerInformatik/java-http-server) [![Maven Central](https://img.shields.io/maven-central/v/info.unterrainer.commons/http-server)](https://search.maven.org/artifact/info.unterrainer.commons/http-server) [![Twitter Follow](https://img.shields.io/twitter/follow/throbax.svg?style=social&label=Follow&maxAge=2592000)](https://twitter.com/throbax)



# java-http-server

A wrapper around [Javalin](https://javalin.io/) enabling you to craft database-backed CRUD REST-services in no-time.

This library reduces boilerplate code when setting up a REST-server connected to a relational database (MariaDB adapter exists) providing extensive builders to generate standard CRUD REST-endpoints.

Further it has interceptors and extensions allowing you to interact with the process of generating a response for a specific request at any level. The sync-extensions may abort the standard-process at any point or simply alter the DTOs passed.



## Prerequisites

A relational database (MariaDB) that holds your entities (we use Liquibase for git-supported, structured, versioned DDL-manipulation) and a JPA persistenceUnit for that database.
(You should use our [java-rdb-utils](https://github.com/UnterrainerInformatik/java-rdb-utils) project for this, since it deals with max-accuracy timestamps and LocalDateTime vs. UTC as well as reducing boilerplate code on Liquibase-startup and shutdown).

### Minimal Example

```java
// Get configuration containing necessary environment variables.
configuration = MyProgramConfiguration.read();
// Create an EntityManagerFactory using java-rdb-utils.
// Registers shutdownhook to close emf as well.
EntityManagerFactory emf = 
    dbUtils.createAutoclosingEntityManagerFactory(MyProgram.class, "my-server");

// Create the server.
HttpServer server = HttpServer.builder()
    .applicationName("my-rest-server")
    .jsonMapper(jsonMapper)
    .orikaFactory(orikaFactory)
    .build();

// All handlers are added and considered in order.
// After you're done adding handlers, start the server.
server.start();
```

When starting this server, you'll be able to access the endpoints using Postman or a similar REST client.

### Standard endpoints available

* AppNameHandler
  Path: GET "/"
  Returns: The name of the server
* AppVersionHandler
  Path: GET "/version"
  Returns: The version of the registered version-provider, or the http-server if none given 
* DateTimeHandler
  Path: GET "/datetime"
  Returns: The current date and time on the server in UTC
* HealthHandler
  Path: GET "/health"
  Returns: "healthy" if the server is up and running
* PostmanCollectionHandler
  Path: GET "/postman"
  Returns: The content of the file "src/main/resources/postman_collection.json", if any



## Custom Handlers

The reason why you're doing REST services is that you have some data to expose to your client.
The next example takes such data (a user) and exposes it.

First, let's create the JPA used to read and write to and from the database.
It's linked to the table using JPA annotations.

### User.jpa

```java
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "user")
public class UserJpa extends BasicJpa {

    private String name;
}
```

Then let's create the JSON object. That's the Data-Transfer-Object being sent to and from the server via HTTP. The server does all of the mapping by itself.

### User.json

```java
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class UserJson extends BasicJson {

	private String name;
}
```

And lastly, update the server-code so that we expose the endpoint.

### Server

```java
// omitted for brevity...
// (see first, minimal example)
// Last line here is the creation of the server ending with ".build()"

// Register a custom handler for the resource 'user'.
server.handlerGroupFor(UserJpa.class, UserJson.class, new JpqlDao<UserJpa>(emf, UserJpa.class))
    .path("users")
    .endpoints(Endpoint.ALL)
    .addRoleFor(Endpoint.ALL, RoleBuilder.open())
    .getListInterceptor()
    .query("userName = :userName[string]")
    .build()
    .add();

server.start();
```

Now you can use the resource reachable via '/users'.



## URL Schema

The server uses an all-plural schema, meaning that the resource name is the plural of the word for it.
Additionally all resource-names are lower-case only due to restrictions within Javalin as of this version.

`GET local.myserver.com/users/12`
	to get the user with the ID 12.
	Referred to as 'get-by-ID'.

`GET local.myserver.com/users?size=10&offset=0`
	to get the list of the next 10 users starting with offset 0.
	Referred to as 'get-list'. The result has a global count and prev, next, first, last links.

`POST local.myserver.com/users` with payload `{ "name": "testName" }`
	to persist the new user with name `testName`.
	Referred to as 'create'.

`PUT local.myserver.com/users/12` with payload `{ "name": "testName1" }`
	to update the name of the user with the ID 12 to `testName1`.
	Referred to as 'full-update'.

`DEL local.myserver.com/users/12`
	to delete the user with the ID 12.
	Referred to as 'delete'.



## Standard Request-Response Process

When sending requests to the server, it will do the following in the following order to get to returning a response object.

![standard-request-response-process-simple](https://github.com/UnterrainerInformatik/java-http-server/raw/master/docs/standard-request-response-process-simple.png)



## Request-Response Process with Extensions and Interceptors

In addition to the standard process, you may register extensions (sync and async) or any number of get-list-interceptors at your leasure.

![standard-request-response-process](https://github.com/UnterrainerInformatik/java-http-server/raw/master/docs/standard-request-response-process.png)

### Sync-Extensions

Synchronous extensions run in the context of the request-response-process and therefore may alter or stop it. The backdraw is that they stall the request-response-process for as long as it takes executing them of course.

If you have long-running operations, you better choose an async-extension point.

#### Example

```java
server.handlerGroupFor(SomeSingletonJpa.class, SomeSingletonJson.class, new JpqlAsyncDao<SomeSingletonJpa>(emf, SomeSingletonJpa.class))
    .path("cmd/somesingleton")
    .endpoints(Endpoint.ALL)
    .addRoleFor(Endpoint.ALL, RoleBuilder.open())
    .extension()
    .preInsertSync((ctx, em, receivedJson, resultJpa) -> {
        if (someSingletonDao.lockedGetNextWith(em, AsyncState.PROCESSING, AsyncState.PROCESSING) != null)
            throw new ConflictException(
            "A singleton-run is already in progress. Only a single singleton-run is allowed to be running at any given time.");
        resultJpa.setState(AsyncState.PROCESSING);
        resultJpa.setStartedOn(LocalDateTime.now(ZoneOffset.UTC));
        return resultJpa;
    })
    .extension()
    .add();
server.start();
```

The throwing of an HttpException here stops the request-response-process returning the error-message for that exception including the correct status-code.



### Async-Extensions

Run in their own context, detached from the request-response-process and therefore cannot alter or stop it.

#### Example

```java
server.handlerGroupFor(SubscriptionJpa.class, SubscriptionJson.class, subscriptionDao)
    .path("/subscriptions")
    .endpoints(Endpoint.ALL)
    .addRoleFor(Endpoint.ALL, RoleBuilder.open())
    .extension()
    .postDeleteAsync(id -> {
        subscriptionHandler.updateSubscriptions();
    })
    .extension()
    .postInsertAsync((receivedJson, mappedJpa, createdJpa, response) -> {
        subscriptionHandler.updateSubscriptions();
    })
    .extension()
    .postModifyAsync((receivedId, receivedJson, readJpa, mappedJpa, persistedJpa, response) -> {
        subscriptionHandler.updateSubscriptions();
    })
    .add();
server.start();
```

Here we're running `subscriptionHandler.updateSubscriptions()` every time a subscription is changed using our CRUD endpoints.

### Get-List-Interceptors

These are called in order of registration BEFORE calling the standard get-list code.
If any single one of those completes without an exception or without returning false, then all other interceptors will be omitted as well as the standard get-list code. The result of the interceptor will be taken and the response will be built using that data.

This allows you to customize ordering, path-parameters and so on, without you having to write all the necessary code to allow for paging all by yourself over and over again.

They come in two flavors.

#### Query-Based Get-List-Interceptors

The server has an integrated language we called RQL (like in REST query language) that allows you to specify and combine several additional query-parameters and the way those are mapped to the database.

Be cautious when using those and be sure to have the right indexes on your database to support the queries your users are then able to build using your query parameters.

##### Example 1

```java
server.handlerGroupFor(SubscriptionJpa.class, SubscriptionJson.class, subscriptionDao)
    .path("/subscriptions")
    .endpoints(Endpoint.ALL)
    .addRoleFor(Endpoint.ALL, RoleBuilder.open())
    .getListInterceptor()
    .query("idString = :stringId[string]")
    .build()
    .add();
server.start();
```

This interceptor is used if the (mandatory) path-parameter `stringId` is set to a value. It is treated as a string internally and is matched using the `=` operator on a database-level to the database-field `idString`. So if you'd pass it the value 'test' the resulting JPQL query would look like this:

```sql
SELECT o from <yourObject> WHERE idString=:stringId
''' with the following parameters being set for that query...
setParam(stringId, "test")
```

You may specify a parameter as optional by pre-fixing the database-field name with a question-mark like so:

##### Example 2 (optional and LIKE)

```java
.getListInterceptor()
    .query("scanId = :scanId[long] AND (?name LIKE :sn[string] OR ?idString LIKE :sn[string] OR ?description LIKE :sn[string])")
    .build()
```

Where `scanId` is a numeric mandatory parameter and the rest is checked using the `LIKE` operator but since the parameter `sn` is optional, the usages are as well.

##### Supported Datatypes

```java
"string"
"boolean", "bool"
"integer", "int"
"long", "lng"
"float"
"double", "dbl"
"datetime"
```

##### Supported Operators

```java
"==", "="
"<>", "!="
"<"
">"
"<="
">="
"IS NULL"
"IS NOT NULL"
"LIKE" 				(string only)
"NOT LIKE" 			(string only)
"STARTSWITH" 		(string only)
"NOT STARTSWITH" 	(string only)
"ENDSWITH" 			(string only)
"NOT ENDSWITH" 		(string only)
```

##### Example 3 (enums)

```java
.getListInterceptor()
	.query("endsOn >= :startOn[datetime] AND ?type = :type[~EventType]")
	.build()
```

This will cast the `String` in the parameter `type` to a enumeration `EventType`.
So this works only if you save the String-representation of an enumeration in the database and not the index.

#### Explicit Get-List-Interceptors

Are registered as anonymous methods returning an `InterceptorData` object or null, if to be omitted.

Here you can do everything the integrated RQL language doesn't make up for.

##### InterceptorData

```java
@Data
@AllArgsConstructor
@Builder
public class InterceptorData {

	private String selectClause;
	private String whereClause;
	private String joinClause;
	private String orderByClause;
	private ParamMap params;
	private String partOfQueryString;
}
```

##### Example

```java
server.handlerGroupFor(SubscriptionJpa.class, SubscriptionJson.class, subscriptionDao)
    .path("/subscriptions")
    .endpoints(Endpoint.ALL)
    .addRoleFor(Endpoint.ALL, RoleBuilder.open())
    .getListInterceptor(subscriptionInterceptor::select)
    .add();
server.start();
```

Where the method `subscriptionInterceptor.select` is some longer method resulting in an `InterceptorData` object being returned like along the lines of this:

```java
public InterceptorData select(final Context ctx, final HandlerUtils hu) {
    // (locNameLike=:string AND locale=:string) AND hasTags=:[long] AND
    // anyTags=:[long] AND
    // state=:string AND quality=:string
    String locNameLike = hu.getQueryParamAsString(ctx, "locNameLike", null);
    String hasTags = hu.getQueryParamAsString(ctx, "hasTags", null);
    ...
```

#### Business-Logic SQL Queries

In order to keep SQL-queries somewhat consistent and because of my deep aversion of Criteria queries, I've used the following 'query language' you can get calling every `JpqlDao<JpaType>`.

##### Examples

```java
// Insert...
userDao.insert(entity).execute();
userDao.insert(entity).entityManager(em).execute();

// Full-Update...
userDao.update(entity).execute();
userDao.update(entity).entityManager(em).execute();

// Single-Result Query...
SingleQueryBuilder<NexusUserJpa, NexusUserJpa> single = userDao.select(32L);
single.delete();
NexusUserJpa en = single.get();

// List-Result Query...
JpaListQuery<NexusUserJpa> query;
query = userDao.select().build();
query = userDao.select().entityManager(em).build();
query = userDao.select().where("o.id = :id").addParam("id", 32L).build();
query = userDao.select()
    .where("o.priority > :priority AND o.enabled = :enabled AND userId IS NOT NULL")
    .addParam("priority", 10L)
    .addParam("enabled", true)
    .desc("o.priority")
    .lockPessimistic()
    .build();
query = userDao.select("o")
    .join("LEFT JOIN GroupJpa g ON g.id = o.groupId")
    .where("g.enabled = :enabled")
    .addParam("enabled", true)
    .build();

// Delete all list-results.
query.delete();

// Upsert first element of list-results.
query.upsert(entity);

// Various ways to get some or all entities from the list-results.
NexusUserJpa a = query.getFirst();
List<NexusUserJpa> b = query.getList();
List<NexusUserJpa> c = query.getList(0, 10);
ListJson<NexusUserJpa> d = query.getListJson();
ListJson<NexusUserJpa> e = query.getListJson(10, 10);
List<NexusUserJpa> f = query.getListReversed();
List<NexusUserJpa> g = query.getN(22);
NexusUserJpa h = query.getSingle();
```

