package br.com.systec.opusfinancial.commons.security;

import java.util.UUID;

public class TenantContext {
    private static final ThreadLocal<UUID> TENANT = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void addTenant(UUID tenantID) {
        TENANT.set(tenantID);
    }

    public static UUID getTenant() {
        return TENANT.get();
    }

    public static void clear() {
        TENANT.remove();
    }
}
