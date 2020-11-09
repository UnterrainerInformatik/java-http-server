![GitHub forks](https://img.shields.io/github/forks/UnterrainerInformatik/java-http-server?style=social) ![GitHub stars](https://img.shields.io/github/stars/UnterrainerInformatik/java-http-server?style=social) ![GitHub repo size](https://img.shields.io/github/repo-size/UnterrainerInformatik/java-http-server) [![GitHub issues](https://img.shields.io/github/issues/UnterrainerInformatik/java-http-server)](https://github.com/UnterrainerInformatik/java-http-server/issues)

[![license](https://img.shields.io/github/license/unterrainerinformatik/FiniteStateMachine.svg?maxAge=2592000)](http://unlicense.org) [![Travis-build](https://travis-ci.org/UnterrainerInformatik/java-http-server.svg?branch=master)](https://travis-ci.org/github/UnterrainerInformatik/java-http-server) [![Maven Central](https://img.shields.io/maven-central/v/info.unterrainer.commons/http-server)](https://search.maven.org/artifact/org.webjars.npm/http-server) [![Twitter Follow](https://img.shields.io/twitter/follow/throbax.svg?style=social&label=Follow&maxAge=2592000)](https://twitter.com/throbax)



# java-http-server

A wrapper around [Javalin](https://javalin.io/) enabling you to craft CRUD REST-services in no-time.

This library reduces boilerplate code when setting up a REST-server connected to a relational database (MariaDB adapter exists) providing extensive builders to generate standard CRUD REST-endpoints.

Further it has interceptors and extensions allowing you to interact with the process of generating a response for a specific request at any level.



## Standard Request-Response Process

![standard-request-response-process-simple](https://github.com/UnterrainerInformatik/java-http-server/raw/master/docs/standard-request-response-process-simple.png)



## Request-Response Process with Extensions and Interceptors

![standard-request-response-process](https://github.com/UnterrainerInformatik/java-http-server/raw/master/docs/standard-request-response-process.png)