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

#### Persistence.xml

Of course you can use your own. This example is only given as a reference and quick-start support.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="3.0" 
    xmlns="https://jakarta.ee/xml/ns/persistence" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd">

   
	<persistence-unit name="my-persistence-unit" transaction-type="RESOURCE_LOCAL">
		<!-- Hibernate specific -->
		<provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
		
		<class>info.unterrainer.commons.rdbutils.entities.BasicJpa</class>
		<class>info.unterrainer.commons.rdbutils.converters.LocalDateTimeConverter</class>
		
		<properties>
			<!-- Hibernate-specific / MariaDB-JDBC-driver specific -->
			<property name="jakarta.persistence.jdbc.driver" value="org.mariadb.jdbc.Driver" />
			<property name="jakarta.persistence.lock.timeout" value="10000" />
			<property name="jakarta.persistence.query.timeout" value="60000" />
			<property name="hibernate.connection.driver_class" value="org.mariadb.jdbc.Driver" />
			<property name="hibernate.dialect" value="org.hibernate.dialect.MariaDBDialect" />
			<property name="hibernate.temp.use_jdbc_metadata_defaults" value="false" />
			<property name="hibernate.jdbc.time_zone" value="UTC"/>
			
			<property name="hibernate.show_sql" value="false" />
			<property name="hibernate.format_sql" value="false" />
			<property name="hibernate.c3p0.min_size" value="5" />
			<property name="hibernate.c3p0.max_size" value="200" />
			<!-- Seconds a Connection can remain pooled but unused before being discarded. Zero means idle connections never expire. -->
			<property name="hibernate.c3p0.timeout" value="300" />
			<!-- The size of c3p0’s global PreparedStatement cache over all connections. Zero means statement caching is turned off. -->
			<property name="hibernate.c3p0.max_statements" value="500" />
			<!-- If this is a number greater than 0, c3p0 will test all idle, pooled but unchecked-out connections, every this number of seconds. -->
			<property name="hibernate.c3p0.idle_test_period" value="3000" />
			<!-- Is set to true, the connection is tested with a simple query before being returned to a user -->
			<property name="hibernate.c3p0.testConnectionOnCheckout" value="true" />
			<property name="hibernate.c3p0.statementCacheNumDeferredCloseThreads" value="1" />

		</properties>
	</persistence-unit>
</persistence>
```

#### Code

```java
// To get the necessary configuration values, we use environment variables.
// You can see the necessary fields in the java-rdb-utils project (configuration).
// Those currently are:
//
// DB_SERVER the IP or URI of your server
// DB_PORT
// DB_NAME
// DB_USER
// DB_PASSWORD

// Create an EntityManagerFactory using java-rdb-utils.
// Registers shutdownhook to close emf as well.
// This method gets the connection data from the environment variables
// as stated above.
// "my-server" is the persistence-unit-name
EntityManagerFactory emf = 
    dbUtils.createAutoclosingEntityManagerFactory(MyProgram.class, "my-persistence-unit");

// Create the server.
HttpServer server = HttpServer.builder()
    .applicationName("my-rest-server")
    .jsonMapper(jsonMapper)
    .objectMapper(objectMapper)
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
    .query("o.userName = :userName[string]")
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
    .query("o.idString = :stringId[string]")
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
    .query("o.scanId = :scanId[long] AND (?o.name LIKE :sn[string] OR ?o.idString LIKE :sn[string] OR ?o.description LIKE :sn[string])")
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
	.query("o.endsOn >= :startOn[datetime] AND ?o.type = :type[~EventType]")
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

#### Tenant Capability

The system allows to save different tennants within the same tables, in order to ease development.

This is done on a per-DAO basis (per -table so to say), as there has to be a separate table holding permission-data regarding the tenant-IDs and corresponding reference-IDs.

```bash
--- Person
id		(Long)
name	(String)

--- Person_Permission
id				(Long)
referenceId		(Long)
tenantId		(Long)

----------------------------------------
--- Person
1	Peter
2	Paul
3	Mary

--- Person_Permission
1	1	1
2	2	1
2	3	2
```

When a user having tenantId=1 associated will query the full list of Persons, he will inevitably receive 'Peter and Paul', whereas another user associated with tennantId=2 would receive 'Mary'.

##### Data-Side

To enable this feature, you have to generate a special DAO that is linked to the corresponding permissions-DAO by specifying the JPA-type of the permission-DAO and both the name of the reference-ID and tenant-ID field (all that is explained in the constructor of the DAO).

So you have to create the appropriate permission-table, a permission-JPA for the table.

##### User-Side

In order to query those tables accordingly, your querying user has to be associated with a tenant-ID.

In fact there are TWO associations with multiple tenant-IDs there.
The `tenant_read` set, used to determine if a user can see (and therefore modify or delete) a row, and the `tenant_write` set, used to determine how many and which permission-rows there are to write when creating a new row in the main-table.

###### Setting permissions in the DAO

On the DAO-level (if you do DB stuff on the server) you may specify those freely using the according setters in the query-builders of the DAO. When using the DAO you will only have to specify a single set of tenant-IDs since you know how you're planning on using those yourself (if you will create a row, then the tenant-ID set is equivalent to the `tenant_write` set; if you will only query, then specify a tenant-ID set equivalent to the `tenant_read` set).

###### Setting permissions via KeyCloak

In KeyCloak you have to specify both sets per user and the system will pick the appropriate set when manipulating or querying the database.

This is done by settings User-Attributes.

```bash
User: Psilo / Attributes
-- tenant_read:		1,3
-- tenant_write:	1
```

On KeyCloak-Setup, be advised that you have to specify an Attribute-Mapper for both attributes (`Clients-><ClientName>->Mappers, create with name=tenants_read/tenants_write, User Attribute=<name>, Token Claim Name=<name>, Claim JSON Type=String`).

That set up, the Attribute values will be passed on into the JWT token and parsed by Http-Server (the data will be copied to the Context.Attribute Object from where you may retrieve them at any time during a request).
The system will decide automatically which set to use, so that in this example the user `Psilo` will be able to see rows that have the permission for tenant-ID 1 or 3, but when creating a row, it will only write a permission for tenant-ID 1.

##### Example

```java
// Passing the TestPermissionJpa class enables the tenant-capability.
// The TestPermissionJpa has a getReferenceId() and getTenantId() method
// as required by the default setting.
server.handlerGroupFor(TestJpa.class, TestJson.class,
	new JpqlDao<>(emf, TestJpa.class, TestPermissionJpa.class))
.path("/tenanttests")
.endpoints(Endpoint.ALL)
.jsonMapper(mapper)
.addRoleFor(Endpoint.ALL, RoleBuilder.authenticated())...

// The next example sets up tenant-capability using a JPA that has a
// getRefId() and a getTId() method (not default).
server.handlerGroupFor(TestJpa.class, TestJson.class,
	new JpqlDao<>(emf, TestJpa.class, TestPermissionJpa.class, "refId", "tId"))
.path("/tenanttests")
```

