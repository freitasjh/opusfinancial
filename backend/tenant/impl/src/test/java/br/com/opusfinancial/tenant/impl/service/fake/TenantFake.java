package br.com.opusfinancial.tenant.impl.service.fake;



import br.com.systec.opusfinancial.tenant.api.domain.Tenant;
import br.com.systec.opusfinancial.tenant.impl.entity.TenantEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class TenantFake {

    private static final UUID tenantID = UUID.randomUUID();
    private static final UUID ownerID = UUID.randomUUID();

    public static TenantEntity toFake() {
        TenantEntity tenant = new TenantEntity();
        tenant.setId(tenantID);
        tenant.setName("Tenant teste 1");
        tenant.setCreateAt(LocalDateTime.now());
        tenant.setUpdateAt(LocalDateTime.now());
        tenant.setOwnerId(ownerID);

        return tenant;
    }

    public static Tenant toFakeVO() {
        Tenant tenant = new Tenant();
        tenant.setId(tenantID);
        tenant.setName("Tenant teste 1");
        tenant.setOwnerId(ownerID);

        return tenant;
    }
}
