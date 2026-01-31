package br.com.systec.opusfinancial.commons.messaging.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

public record EventPublisherVO(UUID tenantId) implements Serializable {
    @Serial
    private static final long serialVersionUID = 406272709561090817L;

}
