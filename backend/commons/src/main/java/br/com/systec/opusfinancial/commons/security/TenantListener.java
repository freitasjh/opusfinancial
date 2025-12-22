package br.com.systec.opusfinancial.commons.security;

import br.com.systec.opusfinancial.commons.entities.AbstractEntity;
import br.com.systec.opusfinancial.commons.exceptions.TenantNotFountException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.UUID;

public class TenantListener {

    @PrePersist
    @PreUpdate
    public void setTenant(AbstractEntity entity) {
        UUID tenantId = TenantContext.getTenant();

        if(tenantId == null) {
            throw new TenantNotFountException();
        }

        entity.setTenantId(tenantId);
    }
}
