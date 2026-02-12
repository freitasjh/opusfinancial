package br.com.systec.opusfinancial.commons.security;

import br.com.systec.opusfinancial.commons.entities.AbstractEntity;
import br.com.systec.opusfinancial.commons.exceptions.TenantNotFountException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class TenantListener {
    private static final Logger log = LoggerFactory.getLogger(TenantListener.class);

    @PrePersist
    @PreUpdate
    public void setTenant(AbstractEntity entity) {
        log.warn("@@@ Setando tenant na entidade {} @@@", entity.getClass().getName());

        if(entity.getTenantId() != null) {
            log.warn("@@@ Tenant já foi informado @@@");
            return;
        }

        UUID tenantId = TenantContext.getCurrentTenant();

        if(tenantId == null) {
            log.error("@@ Tenant não encontrado @@@");
            throw new TenantNotFountException();
        }

        entity.setTenantId(tenantId);
    }
}
