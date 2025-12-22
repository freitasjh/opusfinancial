package br.com.systec.opusfinancial.commons.exceptions;

import java.io.Serial;

public class TenantNotFountException extends BaseException {
    @Serial
    private static final long serialVersionUID = -5917077530278513193L;

    public TenantNotFountException() {
        super("O tenant não foi informado");
    }
}
