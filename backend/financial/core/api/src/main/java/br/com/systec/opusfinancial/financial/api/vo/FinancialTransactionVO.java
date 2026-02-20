package br.com.systec.opusfinancial.financial.api.vo;

import br.com.systec.opusfinancial.api.vo.CategoryVO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class FinancialTransactionVO {
    private UUID id;
    private AccountVO account;
    private BigDecimal amount;
    private String description;
    private Date createAt;
    private TransactionType transactionType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate paymentAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private CategoryTransactionType categoryTransactionType;
    private Boolean processed;
    private CategoryVO category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime processedAt;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public AccountVO getAccount() {
        return account;
    }

    public void setAccount(AccountVO account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

    public CategoryVO getCategory() {
        return category;
    }

    public void setCategory(CategoryVO category) {
        this.category = category;
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
    }
}
