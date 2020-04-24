package info.unterrainer.commons.httpserver.scripts.jsons;

import info.unterrainer.commons.httpserver.jsons.BasicJson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TestJson extends BasicJson {

	private String message;
}
