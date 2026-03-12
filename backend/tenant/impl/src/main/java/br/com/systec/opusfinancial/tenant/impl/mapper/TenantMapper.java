package br.com.systec.opusfinancial.tenant.impl.mapper;


import br.com.systec.opusfinancial.tenant.api.domain.Tenant;
import br.com.systec.opusfinancial.tenant.impl.entity.TenantEntity;

public class TenantMapper {

    private TenantMapper(){}

    public static TenantMapper of() {
        return new TenantMapper();
    }

    public Tenant toVO(TenantEntity entity) {
        Tenant tenant = new Tenant();
        tenant.setId(entity.getId());
        tenant.setName(entity.getName());
        tenant.setOwnerId(entity.getOwnerId());

        return tenant;
    }

    public TenantEntity toEntity(Tenant tenant) {
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setId(tenant.getId());
        tenantEntity.setName(tenant.getName());
        tenantEntity.setOwnerId(tenant.getOwnerId());

        return tenantEntity;
    }

//    public Tenant accountToTenant(UserAccount accountVO) {
//        Tenant tenantVO = new Tenant();
//        tenantVO.setName(accountVO.getAccountName());
//
//        return tenantVO;
//    }
}
