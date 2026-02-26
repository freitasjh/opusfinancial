package br.com.systec.opusfinancial.financial.core.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class ExpenseTransactionSaveResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5373456152594726821L;

    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
