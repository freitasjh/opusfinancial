package br.com.systec.opusfinancial.financial.api.vo;

import br.com.systec.opusfinancial.api.vo.BankVO;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountVO implements Serializable {

    @Serial
    private static final long serialVersionUID = -2943517337132935760L;
    private UUID id;
    private UUID tenantId;
    private String accountName;
    private BigDecimal balance;
    private AccountType accountType;
    private BankVO bank;
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

    public BankVO getBank() {
        return bank;
    }

    public void setBank(BankVO bankVO) {
        this.bank = bankVO;
    }

    public void setBankId(UUID bankId) {
        if (bank == null) {
            bank = new BankVO();
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
}