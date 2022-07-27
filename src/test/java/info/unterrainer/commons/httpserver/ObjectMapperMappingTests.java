package info.unterrainer.commons.httpserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import info.unterrainer.commons.httpserver.jpas.ChildJpa;
import info.unterrainer.commons.httpserver.jsons.ChildJson;
import info.unterrainer.commons.serialization.objectmapper.ObjectMapper;

public class ObjectMapperMappingTests {

	public static ObjectMapper objectMapper;

	@BeforeAll
	public static void beforeClass() {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void mappingJsonToJpaWorks() {
		LocalDateTime now = LocalDateTime.now();
		ChildJson json = ChildJson.builder().id(4L).message("test").createdOn(now).editedOn(now).build();
		ChildJpa jpa = objectMapper.map(ChildJpa.class, json);
		assertThat(jpa.getId()).isEqualTo(json.getId());
		assertThat(jpa.getMessage()).isEqualTo(json.getMessage());
		assertThat(jpa.getCreatedOn()).isEqualTo(json.getCreatedOn());
		assertThat(jpa.getEditedOn()).isEqualTo(json.getEditedOn());
	}

	@Test
	public void mappingJpaToJsonWorks() {
		LocalDateTime now = LocalDateTime.now();
		ChildJpa jpa = ChildJpa.builder().id(4L).message("test").createdOn(now).editedOn(now).build();
		ChildJson json = objectMapper.map(ChildJson.class, jpa);
		assertThat(json.getId()).isEqualTo(jpa.getId());
		assertThat(json.getMessage()).isEqualTo(jpa.getMessage());
		assertThat(json.getCreatedOn()).isEqualTo(jpa.getCreatedOn());
		assertThat(json.getEditedOn()).isEqualTo(jpa.getEditedOn());
	}
}
