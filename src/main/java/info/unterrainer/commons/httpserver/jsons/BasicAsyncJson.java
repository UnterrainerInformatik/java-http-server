package info.unterrainer.commons.httpserver.jsons;

import info.unterrainer.commons.rdbutils.enums.AsyncState;
import info.unterrainer.commons.serialization.jsons.BasicJson;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
public class BasicAsyncJson extends BasicJson {

	private AsyncState state;
}
