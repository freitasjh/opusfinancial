package br.com.systec.opusfinancial.financial.core.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class IncomingSaveResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1508511895679506345L;

    private UUID id;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
