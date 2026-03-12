package br.com.systec.opusfinancial.identity.impl.fake;

import br.com.systec.opusfinancial.tenant.api.domain.Tenant;

import java.util.UUID;

public class TenantFake {

    private static final UUID tenantID = UUID.randomUUID();
    private static final UUID ownerID = UUID.randomUUID();

    public static Tenant toFakeVO() {
        Tenant tenant = new Tenant();
        tenant.setId(tenantID);
        tenant.setName("Tenant teste 1");
        tenant.setOwnerId(ownerID);

        return tenant;
    }
}
