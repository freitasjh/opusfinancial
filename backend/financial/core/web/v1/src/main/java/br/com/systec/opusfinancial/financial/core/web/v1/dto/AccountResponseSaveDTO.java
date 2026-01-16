package br.com.systec.opusfinancial.financial.core.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class AccountResponseSaveDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -327074198125448645L;

    private UUID id;
    private String accountName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
