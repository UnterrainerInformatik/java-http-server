package info.unterrainer.commons.httpserver.daos;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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

	public ParamMap addParameter(final String parameterKey, final Object parameterValue) {
		parameters.put(parameterKey, parameterValue);
		return this;
	}

	public Object get(final String parameterKey) {
		return parameters.get(parameterKey);
	}

	public Set<Entry<String, Object>> entrySet() {
		return parameters.entrySet();
	}

	public Set<String> keySet() {
		return parameters.keySet();
	}

	public Collection<Object> valueSet() {
		return parameters.values();
	}

	public boolean containsKey(final String parameterKey) {
		return parameters.containsKey(parameterKey);
	}

	public boolean containsValue(final Object parameterValue) {
		return parameters.containsValue(parameterValue);
	}

	public void clear() {
		parameters.clear();
	}

	@Override
	public ParamMap clone() {
		return new ParamMap(new HashMap<String, Object>(parameters));
	}
}
