package info.unterrainer.commons.httpserver.scripts.jsons;

import info.unterrainer.commons.serialization.jsons.BasicJson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = true, chain = true)
public class TestJson extends BasicJson {

	private String message;
}
