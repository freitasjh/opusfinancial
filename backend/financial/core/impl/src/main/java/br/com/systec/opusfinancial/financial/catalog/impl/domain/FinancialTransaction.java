package br.com.systec.opusfinancial.financial.catalog.impl.domain;

import br.com.systec.opusfinancial.commons.entities.AbstractEntity;
import br.com.systec.opusfinancial.financial.api.vo.CategoryTransactionType;
import br.com.systec.opusfinancial.financial.api.vo.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "financial_transaction")
public class FinancialTransaction extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = -2680341404461111327L;

    @Column(name = "description")
    private String description;
    @Column(name = "payment_at")
    private LocalDate paymentAt;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "account_id")
    private UUID accountId;
    @Column(name = "category_id")
    private UUID categoryId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @Column(name = "category_transaction_type")
    @Enumerated(EnumType.STRING)
    private CategoryTransactionType categoryTransactionType;
    @Column(name = "processed")
    private Boolean processed;
    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getPaymentAt() {
        return paymentAt;
    }

    public void setPaymentAt(LocalDate paymentAt) {
        this.paymentAt = paymentAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public CategoryTransactionType getCategoryTransactionType() {
        return categoryTransactionType;
    }

    public void setCategoryTransactionType(CategoryTransactionType categoryTransactionType) {
        this.categoryTransactionType = categoryTransactionType;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}
