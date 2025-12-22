package br.com.systec.opusfinancial.security.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;

import java.io.Serial;

public class SecurityException extends BaseException {
    @Serial
    private static final long serialVersionUID = -2035670618368637537L;

    public SecurityException(Throwable e) {
        super("Ocorreu um erro ao tentar aplicar as seguranças",e);
    }

    public SecurityException(String message) {
        super(message);
    }

    public SecurityException(String message, Throwable cause) {
        super(message, cause);
    }
}
