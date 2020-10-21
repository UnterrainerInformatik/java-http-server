package info.unterrainer.commons.httpserver.rql;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RqlDataElement {

	private int index;
	private RqlDataType type;
	private String value;
}
