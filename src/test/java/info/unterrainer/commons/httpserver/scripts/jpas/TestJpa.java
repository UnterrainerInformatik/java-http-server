package info.unterrainer.commons.httpserver.scripts.jpas;

import javax.persistence.Entity;
import javax.persistence.Table;

import info.unterrainer.commons.rdbutils.entities.BasicEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "test")
public class TestJpa extends BasicEntity {

	private String message;
}
