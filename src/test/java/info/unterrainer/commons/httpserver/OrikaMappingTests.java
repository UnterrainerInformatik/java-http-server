package info.unterrainer.commons.httpserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.BeforeClass;
import org.junit.Test;

import info.unterrainer.commons.httpserver.jpas.ChildJpa;
import info.unterrainer.commons.httpserver.jsons.ChildJson;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class OrikaMappingTests {

	public static MapperFactory orikaFactory = new DefaultMapperFactory.Builder().build();
	public static MapperFacade orikaMapper;

	@BeforeClass
	public static void beforeClass() {
		orikaMapper = orikaFactory.getMapperFacade();
	}

	@Test
	public void mappingJsonToJpaWorks() {
		LocalDateTime now = LocalDateTime.now();
		ChildJson json = ChildJson.builder().id(4L).message("test").createdOn(now).editedOn(now).build();
		ChildJpa jpa = orikaMapper.map(json, ChildJpa.class);
		assertThat(jpa.getId()).isEqualTo(json.getId());
		assertThat(jpa.getMessage()).isEqualTo(json.getMessage());
		assertThat(jpa.getCreatedOn()).isEqualTo(json.getCreatedOn());
		assertThat(jpa.getEditedOn()).isEqualTo(json.getEditedOn());
	}

	@Test
	public void mappingJpaToJsonWorks() {
		LocalDateTime now = LocalDateTime.now();
		ChildJpa jpa = ChildJpa.builder().id(4L).message("test").createdOn(now).editedOn(now).build();
		ChildJson json = orikaMapper.map(jpa, ChildJson.class);
		assertThat(json.getId()).isEqualTo(jpa.getId());
		assertThat(json.getMessage()).isEqualTo(jpa.getMessage());
		assertThat(json.getCreatedOn()).isEqualTo(jpa.getCreatedOn());
		assertThat(json.getEditedOn()).isEqualTo(jpa.getEditedOn());
	}
}
