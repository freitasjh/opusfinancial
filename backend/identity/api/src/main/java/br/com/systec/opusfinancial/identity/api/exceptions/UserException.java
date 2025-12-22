package br.com.systec.opusfinancial.identity.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;

import java.io.Serial;

public class UserException extends BaseException {

    @Serial
    private static final long serialVersionUID = 2417410470511839465L;

    public UserException() {
    }

    public UserException(Throwable e) {
        super(e);
    }

    public UserException(String message) {
        super(message);
    }
}
