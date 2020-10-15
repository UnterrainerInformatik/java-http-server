package info.unterrainer.commons.httpserver.accessmanager;

import io.javalin.core.security.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NamedRole implements Role {

	@Getter
	public final String name;
}
