package info.unterrainer.commons.httpserver.jsons;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class BasicJson {

	@JsonProperty
	private Long id;
	@JsonProperty
	private LocalDateTime createdOn;
	@JsonProperty
	private LocalDateTime editedOn;
}
