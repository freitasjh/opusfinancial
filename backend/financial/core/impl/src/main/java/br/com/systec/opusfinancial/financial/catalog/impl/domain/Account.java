package br.com.systec.opusfinancial.financial.catalog.impl.domain;

import br.com.systec.opusfinancial.commons.entities.AbstractEntity;
import br.com.systec.opusfinancial.financial.api.vo.AccountType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "account")
public class Account extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = -702544071222552985L;

    @Column(name = "account_name")
    private String accountName;
    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "balance")
    private BigDecimal balance;
    @JoinColumn(name = "bank_id")
    private UUID bankId;

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
}
