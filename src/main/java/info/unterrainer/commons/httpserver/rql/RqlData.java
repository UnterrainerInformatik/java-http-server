package info.unterrainer.commons.httpserver.rql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RqlData {
	private List<RqlDataElement> parsedCommand = new ArrayList<>();
	private Map<String, Object> params = new HashMap<>();
	private Map<String, String> queryString = new HashMap<>();

	public String getParsedCommandAsString() {
		return String.join("", parsedCommand.stream().map(RqlDataElement::getValue).collect(Collectors.toList()));
	}

	public String getQueryStringAsString() {
		return String.join("&",
				queryString.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).collect(Collectors.toList()));
	}
}
