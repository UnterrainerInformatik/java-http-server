package info.unterrainer.commons.httpserver.daos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class UpsertResult<T> {

	private T jpa;
	private boolean wasInserted;
	private boolean wasUpdated;
}
