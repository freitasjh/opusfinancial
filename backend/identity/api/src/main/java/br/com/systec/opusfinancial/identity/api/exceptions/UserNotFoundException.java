package br.com.systec.opusfinancial.identity.api.exceptions;

import java.io.Serial;

public class UserNotFoundException extends UserException{
    @Serial
    private static final long serialVersionUID = 8565663991235934392L;

    public UserNotFoundException() {
        super("Usuario não encontrado");
    }
}
