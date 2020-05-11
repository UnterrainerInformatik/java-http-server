package info.unterrainer.commons.httpserver.daos;

import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@Builder
public class ParamMap {

	@Singular
	private Map<String, Object> parameters;
}
