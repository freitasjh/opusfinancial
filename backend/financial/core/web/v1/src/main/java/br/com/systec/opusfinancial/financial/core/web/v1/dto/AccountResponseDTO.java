package br.com.systec.opusfinancial.financial.core.web.v1.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class AccountResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -9189134775478362372L;

    private UUID id;
    private String accountName;
    private String bank;
    private String accountType;

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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
