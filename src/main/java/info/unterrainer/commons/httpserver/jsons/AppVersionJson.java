package info.unterrainer.commons.httpserver.jsons;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class AppVersionJson {

	private String name;
	private String buildTime;
	private String pomVersion;
}
