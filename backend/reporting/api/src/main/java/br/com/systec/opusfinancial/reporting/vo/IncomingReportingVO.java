package br.com.systec.opusfinancial.reporting.vo;

import br.com.systec.opusfinancial.reporting.util.DateUtils;
import jakarta.persistence.Tuple;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

public class IncomingReportingVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8106227663130133432L;

    private UUID id;
    private BigDecimal amount;
    private LocalDate createAt;
    private LocalDate paymentAt;
    private String description;
    private String category;
    private String account;

    public IncomingReportingVO(Tuple tuple) {
        this.id = tuple.get("expense_id", UUID.class);
        this.amount = tuple.get("expense_amount", BigDecimal.class);
        this.createAt = DateUtils.timeStampToLocalDate(tuple.get("expense_create_at", Timestamp.class));
        this.paymentAt = DateUtils.dateSqlToLocalDate(tuple.get("expense_payment_at", Date.class));
        this.description = tuple.get("expense_description", String.class);
        this.category = tuple.get("expense_category", String.class);
        this.account = tuple.get("expense_account", String.class);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getPaymentAt() {
        return paymentAt;
    }

    public void setPaymentAt(LocalDate paymentAt) {
        this.paymentAt = paymentAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

}
