package info.unterrainer.commons.httpserver;

import java.util.Set;

import io.javalin.core.security.Role;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = true)
class HandlerInstance {
	private HandlerType handlerType;
	private String path;
	private Handler handler;
	@Singular
	private Set<Role> roles;
}
