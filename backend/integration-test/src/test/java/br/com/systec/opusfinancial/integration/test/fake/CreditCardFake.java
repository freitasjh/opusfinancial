package br.com.systec.opusfinancial.integration.test.fake;

import br.com.systec.opusfinancial.financial.creditcard.api.domain.BrandType;
import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCardStatusType;
import br.com.systec.opusfinancial.financial.creditcard.web.internal.v1.dto.CreditCardInputDTO;
import br.com.systec.opusfinancial.integration.test.util.IntegrationUtil;

import java.math.BigDecimal;

public class CreditCardFake {

    public static CreditCardInputDTO toInputDTO() {
        return new CreditCardInputDTO(
                null, // ID nulo na criação
                "My Test Card",
                "1234-5678-9012-3456",
                "123",
                "TEST USER",
                BigDecimal.valueOf(5000.00),
                BigDecimal.valueOf(5000.00),
                "10",
                "03",
                BrandType.MASTERCARD,
                CreditCardStatusType.ACTIVE,
                IntegrationUtil.accountId
        );
    }

    public static CreditCardInputDTO toValidateError() {
        return new CreditCardInputDTO(
                null, // ID nulo na criação
                null,
                "1234-5678-9012-3456",
                "123",
                "TEST USER",
                null,
                BigDecimal.valueOf(5000.00),
                "10",
                "03",
                BrandType.MASTERCARD,
                CreditCardStatusType.ACTIVE,
                IntegrationUtil.accountId
        );
    }
}
