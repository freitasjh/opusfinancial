package br.com.systec.opusfinancial.identity.impl.mapper;

import br.com.systec.opusfinancial.identity.api.vo.UserAccountVO;
import br.com.systec.opusfinancial.identity.api.vo.TenantVO;
import br.com.systec.opusfinancial.identity.impl.entities.Tenant;

public class TenantMapper {

    private TenantMapper(){}

    public static TenantMapper of() {
        return new TenantMapper();
    }

    public TenantVO toVO(Tenant tenant) {
        TenantVO tenantVO = new TenantVO();
        tenantVO.setId(tenant.getId());
        tenantVO.setName(tenant.getName());
        tenantVO.setOwnerId(tenant.getOwnerId());

        return tenantVO;
    }

    public Tenant toEntity(TenantVO tenantVO) {
        Tenant tenant = new Tenant();
        tenant.setId(tenantVO.getId());
        tenant.setName(tenantVO.getName());
        tenant.setOwnerId(tenantVO.getOwnerId());

        return tenant;
    }

    public TenantVO accountToTenant(UserAccountVO accountVO) {
        TenantVO tenantVO = new TenantVO();
        tenantVO.setName(accountVO.getAccountName());

        return tenantVO;
    }
}
