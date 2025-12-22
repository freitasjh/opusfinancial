package br.com.systec.opusfinancial.security.api.exceptions;

import java.io.Serial;

public class RefreshTokenNotFoundException extends SecurityException {
    @Serial
    private static final long serialVersionUID = 9156858815425194632L;


    public RefreshTokenNotFoundException() {
        super("refresh.token.not.found");
    }

    public RefreshTokenNotFoundException(Throwable e) {
        super(e);
    }
}
