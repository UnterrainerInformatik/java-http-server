package info.unterrainer.commons.httpserver.daos;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Singular;

@Data
@AllArgsConstructor
@Builder
public class ParamMap {

	@Singular
	private Map<String, Object> parameters;

	@Override
	protected ParamMap clone() throws CloneNotSupportedException {
		return new ParamMap(new HashMap<String, Object>(parameters));
	}
}
