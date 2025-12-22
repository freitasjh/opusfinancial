package br.com.systec.opusfinancial.identity.api.exceptions;

import java.io.Serial;

public class UserUsernameOrEmailExistException extends UserException {
    @Serial
    private static final long serialVersionUID = 9016518893718040362L;

    public UserUsernameOrEmailExistException() {
        super("Email ou usuario já existe");
    }
}
