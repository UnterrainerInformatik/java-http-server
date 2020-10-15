package info.unterrainer.commons.httpserver.accessmanager;

import io.javalin.core.security.Role;

enum DefaultRole implements Role {
	OPEN,
	AUTHENTICATED;
}
