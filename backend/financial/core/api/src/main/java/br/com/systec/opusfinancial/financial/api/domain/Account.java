package br.com.systec.opusfinancial.financial.api.domain;

import br.com.systec.opusfinancial.api.domain.Bank;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Account implements Serializable {

    @Serial
    private static final long serialVersionUID = -2943517337132935760L;
    private UUID id;
    private UUID tenantId;
    private String accountName;
    private BigDecimal balance;
    private AccountType accountType;
    private Bank bank;
    private String accountNumber;
    private String agency;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bankVO) {
        this.bank = bankVO;
    }

    public void setBankId(UUID bankId) {
        if (bank == null) {
            bank = new Bank();
        }

        bank.setId(bankId);
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", tenantId=" + tenantId +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                ", accountType=" + accountType +
                ", bank=" + bank +
                ", accountNumber='" + accountNumber + '\'' +
                ", agency='" + agency + '\'' +
                '}';
    }
}