package info.unterrainer.commons.httpserver.daos;

import java.lang.reflect.Method;

import info.unterrainer.commons.httpserver.exceptions.InternalServerErrorException;
import info.unterrainer.commons.httpserver.jpas.BasicPermissionJpa;
import lombok.Getter;

public class TenantData {

	@Getter
	private Class<? extends BasicPermissionJpa> permissionJpaType;
	@Getter
	private String mainTableIdReferenceField = "referenceId";
	@Getter
	private String tenantIdField = "tenantId";

	private Method referenceSetMethod;
	private Method tenantIdSetMethod;

	public TenantData(final Class<? extends BasicPermissionJpa> permissionJpaType) {
		super();
		this.permissionJpaType = permissionJpaType;
	}

	public TenantData(final Class<? extends BasicPermissionJpa> permissionJpaType,
			final String mainTableIdReferenceField, final String tenantIdField) {
		super();
		this.permissionJpaType = permissionJpaType;
		this.mainTableIdReferenceField = mainTableIdReferenceField;
		this.tenantIdField = tenantIdField;
	}

	public Method getReferenceSetMethod() {
		if (referenceSetMethod == null)
			try {
				referenceSetMethod = permissionJpaType.getMethod("set" + capitalize(mainTableIdReferenceField),
						Long.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new InternalServerErrorException(
						String.format("Error creating permission-entry in [%s]", permissionJpaType.getSimpleName()), e);
			}
		return referenceSetMethod;
	}

	public Method getTenantIdSetMethod() {
		if (tenantIdSetMethod == null)
			try {
				tenantIdSetMethod = permissionJpaType.getMethod("set" + capitalize(tenantIdField), Long.class);
			} catch (NoSuchMethodException | SecurityException e) {
				throw new InternalServerErrorException(
						String.format("Error creating permission-entry in [%s]", permissionJpaType.getSimpleName()), e);
			}
		return tenantIdSetMethod;
	}

	private String capitalize(final String s) {
		if (s == null || s.isEmpty())
			return s;
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
}
