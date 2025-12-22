package br.com.systec.opusfinancial.identity.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;

import java.io.Serial;

public class TenantException extends BaseException {
    @Serial
    private static final long serialVersionUID = -5133090646135302668L;

    public TenantException(String message) {
        super(message);
    }

    public TenantException() {
        super("Ocorreu um erro ao tentar salvar o tenant");
    }

    public TenantException(Throwable e) {
        super("Ocorreu um erro ao tentar salvar o tenant",e);
    }
}
