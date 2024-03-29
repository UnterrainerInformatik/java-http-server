package info.unterrainer.commons.httpserver.jpas;

import jakarta.persistence.MappedSuperclass;

import info.unterrainer.commons.rdbutils.entities.BasicJpa;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public class BasicPermissionJpa extends BasicJpa {

	private Long referenceId;
	private Long tenantId;
}
