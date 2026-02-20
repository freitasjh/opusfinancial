package br.com.systec.opusfinancial.financial.core.web.v1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class IncomingInformationResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1167491805376605222L;

    private UUID id;
    private String description;
    private String account;
    private String category;
    private BigDecimal amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate paymentAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate processedAt;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getPaymentAt() {
        return paymentAt;
    }

    public void setPaymentAt(LocalDate paymentAt) {
        this.paymentAt = paymentAt;
    }

    public LocalDate getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDate processedAt) {
        this.processedAt = processedAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
