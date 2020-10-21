package info.unterrainer.commons.httpserver.rql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import info.unterrainer.commons.httpserver.HandlerUtils;
import info.unterrainer.commons.httpserver.exceptions.BadRequestException;

@ExtendWith(MockitoExtension.class)
public class RqlParserTest {

	private HandlerUtils hu;
	private RqlUtils rqlUtils;

	private static List<Arguments> provideStringsForRqlUtilTest() {
		return List.of(Arguments.of("userId=:userId", "userId = :userId", Map.of("userId", "1234")),
				Arguments.of("userId=:userId AND (timeFrom<=:timeFrom OR timeTo>:timeTo)",
						"userId = :userId AND (timeFrom <= :timeFrom OR timeTo > :timeTo)",
						Map.of("userId", "1234", "timeFrom", "2020-09-13", "timeTo", "2020-10-24")),
				Arguments.of("startsOn=:startsOn AND endsOn=:endsOn AND ?isOpen=:isOpen AND ?type=:type",
						"startsOn = :startsOn AND endsOn = :endsOn AND isOpen = :isOpen AND type = :type",
						Map.of("startsOn", "2020-01-01", "endsOn", "2020-02-14", "isOpen", "true", "type", "EVENT")),
				Arguments.of("?offset=:offset AND ?size=:size", "offset = :offset AND size = :size",
						Map.of("offset", "0", "size", "10")),
				Arguments.of(
						"scanId = :scanId AND (searchName LIKE :searchString OR name LIKE :searchString OR opcIdString LIKE :searchString OR description LIKE :searchString)",
						"scanId = :scanId AND (searchName LIKE :searchString OR name LIKE :searchString OR opcIdString LIKE :searchString OR description LIKE :searchString)",
						Map.of("scanId", "1", "searchString", "search-string")),
				Arguments.of(
						"scanId = :scanId AND (searchName LIKE :searchString OR ?name LIKE :searchString2 OR opcIdString LIKE :searchString OR description LIKE :searchString)",
						"scanId = :scanId AND (searchName LIKE :searchString OR opcIdString LIKE :searchString OR description LIKE :searchString)",
						Map.of("scanId", "1", "searchString", "search-string")),
				Arguments.of(
						"scanId = :scanId AND (?searchName LIKE :searchString2 OR ?name LIKE :searchString2 OR ?opcIdString LIKE :searchString2 OR ?description LIKE :searchString2)",
						"scanId = :scanId", Map.of("scanId", "1")),
				Arguments.of(
						"?scanId = :scanId AND (?searchName LIKE :searchString2 OR ?name LIKE :searchString2 AND ?opcIdString LIKE :searchString2)",
						"", Map.of()),
				Arguments.of("?scanId = :scanId AND (?searchName = :s1 AND (?name < :s2 OR ?opcIdString > :s3))",
						"scanId = :scanId AND (searchName = :s1 AND (name < :s2 OR opcIdString > :s3))",
						Map.of("scanId", "1", "s1", "a1", "s2", "a2", "s3", "a3")),
				Arguments.of("?scanId = :scanId AND (?searchName = :s1 AND (?name < :s2 OR ?opcIdString > :s3))",
						"(searchName = :s1 AND (name < :s2 OR opcIdString > :s3))",
						Map.of("s1", "a1", "s2", "a2", "s3", "a3")),
				Arguments.of("?scanId = :scanId AND (?searchName = :s1 AND (?name < :s2 OR ?opcIdString > :s3))",
						"scanId = :scanId AND (searchName = :s1)", Map.of("scanId", "1", "s1", "a1")),
				Arguments.of("?scanId = :scanId AND (?searchName = :s1 AND (?name < :s2 OR ?opcIdString > :s3))",
						"scanId = :scanId AND ((name < :s2 OR opcIdString > :s3))",
						Map.of("scanId", "1", "s2", "a2", "s3", "a3")),
				Arguments.of("?scanId = :scanId AND (?searchName = :s1 AND (?name < :s2 OR ?opcIdString > :s3))",
						"scanId = :scanId AND (searchName = :s1 AND (name < :s2))",
						Map.of("scanId", "1", "s1", "a1", "s2", "a2")),
				Arguments.of("?scanId = :scanId AND (?searchName = :s1 AND (?name < :s2 OR ?opcIdString > :s3))",
						"scanId = :scanId AND (searchName = :s1 AND (opcIdString > :s3))",
						Map.of("scanId", "1", "s1", "a1", "s3", "a3")),
				Arguments.of("?searchName = :s1 AND ?name < :s2", "searchName = :s1 AND name < :s2",
						Map.of("s1", "a1", "s2", "a2")),
				Arguments.of("?searchName = :s1 AND ?name < :s2", "name < :s2", Map.of("s2", "a2")),
				Arguments.of("?searchName = :s1 AND ?name < :s2", "searchName = :s1", Map.of("s1", "a1")),
				Arguments.of("(?searchName = :s1 AND ?name < :s2)", "(searchName = :s1 AND name < :s2)",
						Map.of("s1", "a1", "s2", "a2")),
				Arguments.of("(?searchName = :s1 AND ?name < :s2)", "", Map.of()));
	}

	@ParameterizedTest
	@MethodSource("provideStringsForRqlUtilTest")
	public void rqlUtilTest(final String input, final String expected, final Map<String, String> queryParams) {
		setupMocks(queryParams);
		RqlData result = rqlUtils.parseRql(input);
		assertThat(result.getParsedCommandAsString()).isEqualTo(expected);
		assertThat(
				result.getParams().entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> e.getValue() + "")))
						.containsAllEntriesOf(queryParams);
	}

	private void setupMocks(final Map<String, String> queryParams) {
		hu = mock(HandlerUtils.class);
		rqlUtils = new RqlUtils(null, hu);

		lenient().when(hu.getQueryParamAsString(any(), anyString())).thenAnswer(invocation -> {
			String name = invocation.getArgument(1, String.class);
			String value = queryParams.get(name);
			if (value == null)
				throw new BadRequestException(String.format("Parameter %s is mandatory", name));
			return value;
		});
		lenient().when(hu.getQueryParamAsString(any(), anyString(), any())).thenAnswer(invocation -> {
			String name = invocation.getArgument(1, String.class);
			String value = queryParams.get(name);
			if (value == null)
				return invocation.getArgument(2, String.class);
			return value;
		});
	}
}
