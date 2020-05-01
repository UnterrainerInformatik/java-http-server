package info.unterrainer.commons.httpserver.jsons;

import java.util.List;
import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ListJson<T> {

	@Builder.Default
	private List<T> entries = new ArrayList<>();
	private long count;

	private String first;
	private String last;
	private String next;
	private String previous;
}
