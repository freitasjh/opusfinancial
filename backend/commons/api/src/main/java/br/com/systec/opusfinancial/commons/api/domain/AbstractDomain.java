package br.com.systec.opusfinancial.commons.api.domain;

import java.io.Serial;
import java.util.UUID;

public class AbstractDomain extends BaseDomain {
    @Serial
    private static final long serialVersionUID = -7045116942516294326L;

    protected UUID tenantId;

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }
}
