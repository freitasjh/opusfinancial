package br.com.systec.opusfinancial.financial.creditcard.impl.entity;

import br.com.systec.opusfinancial.commons.entities.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "credit_card_installment")
public class CreditCardInstallmentEntity extends BaseEntity {
    @Serial
    private static final long serialVersionUID = 7823017171233755256L;

    @Column(name = "description")
    private String description;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "installment")
    private int installment;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private CreditCardTransactionEntity transaction;
    @Column(name = "credit_card_invoice_id")
    private Long creditCardInvoiceId;
    @Column(name = "paid_at")
    private LocalDate paidAt;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public CreditCardTransactionEntity getTransaction() {
        return transaction;
    }

    public void setTransaction(CreditCardTransactionEntity transaction) {
        this.transaction = transaction;
    }

    public Long getCreditCardInvoiceId() {
        return creditCardInvoiceId;
    }

    public void setCreditCardInvoiceId(Long creditCardInvoiceId) {
        this.creditCardInvoiceId = creditCardInvoiceId;
    }

    public LocalDate getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDate paidAt) {
        this.paidAt = paidAt;
    }
}
