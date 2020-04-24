package info.unterrainer.commons.httpserver.jsons;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicJson {

	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime editedOn;
}
