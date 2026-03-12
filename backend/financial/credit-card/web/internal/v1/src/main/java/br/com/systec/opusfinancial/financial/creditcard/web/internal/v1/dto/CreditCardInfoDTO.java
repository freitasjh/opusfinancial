package br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CreditCardInfoDTO(
        UUID id,
        String name,
        BigDecimal limit,
        String account,
        String dueDay,
        String closingDate) {
}
