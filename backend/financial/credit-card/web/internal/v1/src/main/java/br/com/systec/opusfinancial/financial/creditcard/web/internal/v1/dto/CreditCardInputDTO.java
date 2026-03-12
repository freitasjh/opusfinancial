package br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto;

import br.com.systec.opusfinancial.financial.creditcard.api.domain.BrandType;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCardStatusType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public record CreditCardInputDTO(
        UUID id,
        @NotNull(message = "Name is required")
        @NotEmpty(message = "Name is required")
        String name,
        String number,
        String cvc,
        String nameCreditCard,
        @NotNull(message = "Total limit is required")
        BigDecimal totalLimit,
        BigDecimal availableLimit,
        String dueDay,
        String closingDate,
        BrandType brand,
        CreditCardStatusType status,
        UUID accountID
) implements Serializable {
}
