package info.unterrainer.commons.httpserver.scripts.jpas;

import javax.persistence.Entity;
import javax.persistence.Table;

import info.unterrainer.commons.httpserver.jpas.BasicPermissionJpa;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "test_permission")
public class TestPermissionJpa extends BasicPermissionJpa {
}
