package info.unterrainer.commons.httpserver.extensions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import io.javalin.http.Context;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor()
@Accessors(fluent = true)
public class AsyncExtensionContext {

	private Map<String, Object> parameters = new HashMap<>();

	public AsyncExtensionContext addParameter(final Context ctx, final String parameterKey) {
		parameters.put(parameterKey, ctx.attribute(parameterKey));
		return this;
	}

	public AsyncExtensionContext addParameter(final String parameterKey, final Object parameterValue) {
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
}
