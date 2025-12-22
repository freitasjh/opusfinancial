package br.com.systec.opusfinancial.security.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;

import java.io.Serial;

public class TokenRevokeException extends BaseException {
    @Serial
    private static final long serialVersionUID = 5084458703749381901L;

    public TokenRevokeException() {
        super("token.revoke");
    }
}
