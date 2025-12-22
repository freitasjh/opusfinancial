package br.com.systec.opusfinancial.security.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;

import java.io.Serial;

public class RefreshTokenExpiredException extends BaseException {

    @Serial
    private static final long serialVersionUID = 2847308287781813143L;

    public RefreshTokenExpiredException() {
        super("refresh.token.expired");
    }
}