package br.com.systec.opusfinancial.commons.exceptions;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
    @Serial
    private static final long serialVersionUID = -1L;
    private List<FieldMessage> list;

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);

    }

    public List<FieldMessage> getErrors() {
        return List.copyOf(list);
    }

    public void addError(String fieldName, String messagem) {
        if(list == null) {
            list = new ArrayList<>();
        }
        list.add(new FieldMessage(fieldName, messagem));
    }
}