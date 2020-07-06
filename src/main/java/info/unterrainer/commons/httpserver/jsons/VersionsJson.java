package info.unterrainer.commons.httpserver.jsons;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class VersionsJson {

	private List<AppVersionJson> Versions;
}
