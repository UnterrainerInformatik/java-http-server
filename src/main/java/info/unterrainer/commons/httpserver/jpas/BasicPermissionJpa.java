package info.unterrainer.commons.httpserver.jpas;

import javax.persistence.MappedSuperclass;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public class BasicPermissionJpa extends BasicJpa {

	private Long referenceId;
	private Long tenantId;
}
