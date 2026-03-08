package br.com.systec.opusfinancial.financial.creditcard.impl.entity;

import br.com.systec.opusfinancial.commons.entities.AbstractEntity;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.InvoiceStatusType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serial;
import java.time.LocalDate;

@Entity
@Table(name = "credit_card_invoice")
public class CreditCardInvoice extends AbstractEntity {
    @Serial
    private static final long serialVersionUID = -5536954624935926846L;
    @ManyToOne(cascade = CascadeType.REFRESH, targetEntity = CreditCard.class)
    @JoinColumn(name = "credit_card_id")
    private CreditCard creditCard;
    @Column(name = "status_type")
    @Enumerated(EnumType.STRING)
    private InvoiceStatusType statusType;
    @Column(name = "date_close")
    private LocalDate dateClose;
    @Column(name = "due_date")
    private LocalDate dueDate;
    @Column(name = "date_paid")
    private LocalDate datePaid;


    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public InvoiceStatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(InvoiceStatusType statusType) {
        this.statusType = statusType;
    }

    public LocalDate getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDate dateClose) {
        this.dateClose = dateClose;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(LocalDate datePaid) {
        this.datePaid = datePaid;
    }
}
