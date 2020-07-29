package info.unterrainer.commons.httpserver.daos;

import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class OrderByMap {

	/**
	 * A map of FieldName and a boolean value denoting if it is in descending order
	 * or not (true... DESC, false... ASC).
	 */
	@Singular
	private Map<String, Boolean> orderByFields;
}
