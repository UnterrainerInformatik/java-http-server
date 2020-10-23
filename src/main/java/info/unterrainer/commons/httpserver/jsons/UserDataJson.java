package info.unterrainer.commons.httpserver.jsons;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UserDataJson {

	private String userName;
	private String client;

	private String givenName;
	private String familyName;

	private String email;
	private boolean emailVerified;

	private Set<String> realmRoles;
	private Set<String> clientRoles;

	private boolean isActive;
	private boolean isBearer;
}