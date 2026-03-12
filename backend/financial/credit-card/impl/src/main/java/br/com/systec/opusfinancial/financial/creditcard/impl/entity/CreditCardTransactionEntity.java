package br.com.systec.opusfinancial.financial.creditcard.impl.entity;

import br.com.systec.opusfinancial.commons.impl.entity.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "credit_card_transaction")
public class CreditCardTransactionEntity extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = 4844581979165827806L;

    @Column(name = "description")
    private String description;
    @Column(name = "amount")
    private double amount;
    @Column(name = "installments")
    private int installments;
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "credit_card_id")
    private CreditCardEntity creditCard;
    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<CreditCardInstallmentEntity> listOfInstallment;
    @Column(name = "transactionAt")
    private LocalDate transactionAt;
    @Column(name = "category_id", nullable = false)
    private UUID categoryId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getInstallments() {
        return installments;
    }

    public void setInstallments(int installments) {
        this.installments = installments;
    }

    public CreditCardEntity getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardEntity creditCard) {
        this.creditCard = creditCard;
    }

    public List<CreditCardInstallmentEntity> getListOfInstallment() {
        return listOfInstallment;
    }

    public void setListOfInstallment(List<CreditCardInstallmentEntity> listOfInstallment) {
        this.listOfInstallment = listOfInstallment;
    }

    public LocalDate getTransactionAt() {
        return transactionAt;
    }

    public void setTransactionAt(LocalDate transactionAt) {
        this.transactionAt = transactionAt;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
