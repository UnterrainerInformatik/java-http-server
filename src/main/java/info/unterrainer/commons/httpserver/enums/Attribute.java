package info.unterrainer.commons.httpserver.enums;

public class Attribute {
	public static final String JAVALIN_SERVER = "javalin_server";

	public static final String REQUEST_BODY = "request_body";

	public static final String RESPONSE_OBJECT = "response_object";
	public static final String RESPONSE_STATUS = "response_status";
	public static final String RESPONSE_TYPE = "response_type";
	public static final String RESPONSE_CONTENT_TYPE = "response_content_type";

	public static final String USER_NAME = "user_name";
	public static final String USER_GIVEN_NAME = "user_given_name";
	public static final String USER_FAMILY_NAME = "user_family_name";
	public static final String USER_EMAIL = "user_email";
	public static final String USER_EMAIL_VERIFIED = "user_email_verified";
	public static final String USER_CLIENT = "user_client";
	public static final String USER_CLIENT_ROLES = "user_client_roles";
	public static final String USER_REALM_ROLES = "user_realm_roles";
	public static final String USER_CLIENT_ATTRIBUTE_TENANTS_READ = "user_client_attribute_tenants_read";
	public static final String USER_CLIENT_ATTRIBUTE_TENANTS_WRITE = "user_client_attribute_tenants_write";
	public static final String USER_TENANTS_READ_SET = "user_tenant_read_set";
	public static final String USER_TENANTS_WRITE_SET = "user_tenant_write_set";
	public static final String USER_TENANT_READ = "user_tenant_read";
	public static final String USER_TENANT_WRITE = "user_tenant_write";

	public static final String KEYCLOAK_TOKEN_REJECTION_REASON = "kc_token_rejection_reason";
}
