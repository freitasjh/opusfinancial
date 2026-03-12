package br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.fake;

import br.com.systec.opusfinancial.financial.creditcard.api.domain.BrandType;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCardStatusType;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardInputDTO;

import java.math.BigDecimal;
import java.util.UUID;

public class CreditCardFake {

    public static CreditCardInputDTO createInputDTO() {
        return new CreditCardInputDTO(
                UUID.randomUUID(),
                "My Platinum Card",
                "1234-5678-9012-3456",
                "123",
                "JOHN DOE",
                BigDecimal.valueOf(5000.00),
                BigDecimal.valueOf(2500.00),
                "10",
                "03",
                BrandType.MASTERCARD,
                CreditCardStatusType.ACTIVE,
                UUID.randomUUID()
        );
    }

    public static CreditCard creditCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setId(UUID.randomUUID());
        creditCard.setName("My Platinum Card");

        return creditCard;
    }
}
