package info.unterrainer.commons.httpserver.interceptors;

import info.unterrainer.commons.httpserver.daos.ParamMap;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetListInterceptorResult {

	private String whereClause;
	private ParamMap params;
	private String partOfQueryString;
}
