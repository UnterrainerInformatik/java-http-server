package info.unterrainer.commons.httpserver.accessmanager;

import io.javalin.core.security.Role;

public enum DefaultRoles implements Role {
	PUBLIC, AUTHENTICATED;
}
