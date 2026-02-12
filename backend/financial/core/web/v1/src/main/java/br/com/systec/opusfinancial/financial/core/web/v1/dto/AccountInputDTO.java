package br.com.systec.opusfinancial.financial.core.web.v1.dto;

import br.com.systec.opusfinancial.financial.api.vo.AccountType;
import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountInputDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7143875295779310792L;

    private UUID id;
    @NotNull(message = "Nome da conta não informado")
    private String accountName;
    @NotNull(message = "Informe o tipo de conta")
    private AccountType accountType;
    private BigDecimal balance;
    @NotNull(message = "Banco não informado")
    private UUID bankId;
    private String accountNumber;
    private String agency;

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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UUID getBankId() {
        return bankId;
    }

    public void setBankId(UUID bankId) {
        this.bankId = bankId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }
}
