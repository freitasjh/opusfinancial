package br.com.systec.opusfinancial.commons.exceptions;

import java.io.Serial;

public class MessagingException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6039582535067699122L;

    public MessagingException() {
    }

    public MessagingException(String message) {
        super(message);
    }

    public MessagingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessagingException(Throwable cause) {
        super(cause);
    }
}
