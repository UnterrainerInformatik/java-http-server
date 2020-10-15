package info.unterrainer.commons.httpserver.accessmanager;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleBuilder {

	public DefaultRole open() {
		return DefaultRole.OPEN;
	}

	public DefaultRole authenticated() {
		return DefaultRole.AUTHENTICATED;
	}

	public NamedRole[] named(final String... names) {
		NamedRole[] roles = new NamedRole[names.length];
		for (int i = 0; i < names.length; i++)
			roles[i] = new NamedRole(names[i]);
		return roles;
	}
}
