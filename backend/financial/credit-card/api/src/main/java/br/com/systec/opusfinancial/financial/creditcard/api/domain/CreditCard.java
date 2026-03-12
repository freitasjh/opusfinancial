package br.com.systec.opusfinancial.financial.creditcard.api.domain;

import br.com.systec.opusfinancial.financial.api.domain.Account;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class CreditCard implements Serializable {
    @Serial
    private static final long serialVersionUID = 1634777599605567844L;
    private UUID id;
    private String name;
    private String number;
    private String cvv;
    private String nameCreditCard;
    private BigDecimal limit;
    private BigDecimal availableLimit;
    private String dueDay;
    private String closingDate;
    private BrandType brand;
    private CreditCardStatusType status;
    private Account account;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getNameCreditCard() {
        return nameCreditCard;
    }

    public void setNameCreditCard(String nameCreditCard) {
        this.nameCreditCard = nameCreditCard;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public CreditCard setLimit(BigDecimal limit) {
        this.limit = limit;
        return this;
    }

    public BigDecimal getAvailableLimit() {
        return availableLimit;
    }

    public CreditCard setAvailableLimit(BigDecimal availableLimit) {
        this.availableLimit = availableLimit;
        return this;
    }

    public String getDueDay() {
        return dueDay;
    }

    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public BrandType getBrand() {
        return brand;
    }

    public void setBrand(BrandType brand) {
        this.brand = brand;
    }

    public CreditCardStatusType getStatus() {
        return status;
    }

    public void setStatus(CreditCardStatusType status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
