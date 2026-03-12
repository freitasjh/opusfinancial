package br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto;

import java.io.Serializable;
import java.util.UUID;

public record CreditCardSaveResponseDTO(
        UUID id
) implements Serializable { }
