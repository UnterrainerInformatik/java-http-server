package info.unterrainer.commons.httpserver.scripts.jpas;

import javax.persistence.Entity;
import javax.persistence.Table;

import info.unterrainer.commons.rdbutils.entities.BasicEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
@Accessors(fluent = true, chain = true)
@Entity
@Table(name = "test")
public class TestJpa extends BasicEntity {

	private String message;
}
