package br.com.systec.opusfinancial.commons.exceptions;

public class ObjectFoundException extends BaseException {

    public ObjectFoundException() {
    }

    public ObjectFoundException(String message) {
        super(message);
    }

    public ObjectFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
