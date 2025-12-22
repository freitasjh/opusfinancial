package br.com.systec.opusfinancial.identity.impl.fake;

import br.com.systec.opusfinancial.identity.api.vo.TenantVO;
import br.com.systec.opusfinancial.identity.impl.entities.Tenant;

import java.util.Date;
import java.util.UUID;

public class TenantFake {

    private static final UUID tenantID = UUID.randomUUID();
    private static final UUID ownerID = UUID.randomUUID();

    public static Tenant toFake() {
        Tenant tenant = new Tenant();
        tenant.setId(tenantID);
        tenant.setName("Tenant teste 1");
        tenant.setCreateAt(new Date());
        tenant.setUpdateAt(new Date());
        tenant.setOwnerId(ownerID);

        return tenant;
    }

    public static TenantVO toFakeVO() {
        TenantVO tenant = new TenantVO();
        tenant.setId(tenantID);
        tenant.setName("Tenant teste 1");
        tenant.setOwnerId(ownerID);

        return tenant;
    }
}
