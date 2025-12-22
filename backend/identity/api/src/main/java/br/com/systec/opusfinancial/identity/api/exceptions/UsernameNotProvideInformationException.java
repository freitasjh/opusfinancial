package br.com.systec.opusfinancial.identity.api.exceptions;

import java.io.Serial;

public class UsernameNotProvideInformationException extends UserException {
    @Serial
    private static final long serialVersionUID = -1339142994629964024L;

    public UsernameNotProvideInformationException() {
        super("username.not.provide.information");
    }
}
