package br.com.systec.opusfinancial.financial.creditcard.impl.entity;

import br.com.systec.opusfinancial.commons.entities.AbstractEntity;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.BrandType;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCardStatusType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.io.Serial;
import java.util.UUID;

@Entity
@Table(name = "credit_card")
public class CreditCardEntity extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = -1793559910410745419L;

    @Column(name = "name")
    private String name;
    @Column(name = "number")
    private String number;
    @Column(name = "cvc")
    private String cvc;
    @Column(name = "name_credit_card")
    private String nameCreditCard;
    @Column(name = "total_limit")
    private double totalLimit;
    @Column(name = "available_limit")
    private double availableLimit;
    @Column(name = "due_date")
    private String dueDay;
    @Column(name = "closing_date")
    private String closingDate;
    @Column(name = "brand")
    @Enumerated(EnumType.STRING)
    private BrandType brand;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CreditCardStatusType status;
    @Column(name = "account_id")
    private UUID accountId;

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

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getNameCreditCard() {
        return nameCreditCard;
    }

    public void setNameCreditCard(String nameCreditCard) {
        this.nameCreditCard = nameCreditCard;
    }

    public double getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(double totalLimit) {
        this.totalLimit = totalLimit;
    }

    public double getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(double availableLimit) {
        this.availableLimit = availableLimit;
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

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
