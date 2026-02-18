package br.com.systec.opusfinancial.financial.core.web.v1.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class IncomingFinancialInputDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8813503759204255612L;
    private UUID id;
    private String description;
    @NotNull
    private UUID accountId;
    @NotNull
    private UUID categoryId;
    @NotNull
    private BigDecimal amount;
    private boolean processed;
    @NotNull
    private LocalDate paymentAt;
    private LocalDateTime processedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public LocalDate getPaymentAt() {
        return paymentAt;
    }

    public void setPaymentAt(LocalDate paymentAt) {
        this.paymentAt = paymentAt;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}
