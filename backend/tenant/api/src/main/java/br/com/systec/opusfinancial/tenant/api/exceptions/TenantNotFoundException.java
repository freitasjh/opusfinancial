package br.com.systec.opusfinancial.tenant.api.exceptions;

import java.io.Serial;

public class TenantNotFoundException extends TenantException {
    @Serial
    private static final long serialVersionUID = 6294442345379717043L;

    public TenantNotFoundException() {
        super("Tenant não encontrado");
    }
}
