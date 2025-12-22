package br.com.systec.opusfinancial.identity.api.exceptions;

import java.io.Serial;

public class UserPasswordNotProvideInformationException extends UserException {
    @Serial
    private static final long serialVersionUID = -8113864017870784599L;

    public UserPasswordNotProvideInformationException() {
        super("user.password.not.provide.information");
    }
}
