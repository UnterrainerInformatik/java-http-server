package info.unterrainer.commons.httpserver.interceptors;

import info.unterrainer.commons.httpserver.daos.ParamMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InterceptorData {

	private String selectClause;
	private String whereClause;
	private String joinClause;
	private ParamMap params;
	private String partOfQueryString;
}
