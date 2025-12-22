package br.com.systec.opusfinancial.security.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;

import java.io.Serial;

public class RefreshTokenUsedException extends BaseException {
    @Serial
    private static final long serialVersionUID = 8068516555815886436L;

    public RefreshTokenUsedException() {
        super("refresh.token.used");
    }
}