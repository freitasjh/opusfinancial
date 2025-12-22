package br.com.systec.opusfinancial.security.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;

import java.io.Serial;

public class TokenOpusfinancialExpiredException extends BaseException {
    @Serial
    private static final long serialVersionUID = -2679248854014021808L;

    public TokenOpusfinancialExpiredException() {
        super("token.expire");
    }
    public TokenOpusfinancialExpiredException(Throwable e) {
        super("token.expire", e);
    }
}
