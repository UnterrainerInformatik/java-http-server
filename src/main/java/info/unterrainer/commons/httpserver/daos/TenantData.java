package info.unterrainer.commons.httpserver.daos;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class TenantData {

	private Class<? extends BasicJpa> type;
	private String referenceField;
	private String idField;
}
