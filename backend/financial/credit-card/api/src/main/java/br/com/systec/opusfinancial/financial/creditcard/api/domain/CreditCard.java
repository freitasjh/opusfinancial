package br.com.systec.opusfinancial.financial.creditcard.api.domain;

import br.com.systec.opusfinancial.financial.api.vo.AccountVO;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public class CreditCard implements Serializable {
    @Serial
    private static final long serialVersionUID = 1634777599605567844L;
    private UUID id;
    private String name;
    private String number;
    private String cvc;
    private String nameCreditCard;
    private double totalLimit;
    private double availableLimit;
    private String dueDay;
    private String closingDate;
    private BrandType brand;
    private CreditCardStatusType status;
    private AccountVO account;

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

    public AccountVO getAccount() {
        return account;
    }

    public void setAccount(AccountVO account) {
        this.account = account;
    }
}
