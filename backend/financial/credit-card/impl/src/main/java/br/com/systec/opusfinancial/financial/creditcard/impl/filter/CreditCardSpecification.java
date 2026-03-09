package br.com.systec.opusfinancial.financial.creditcard.impl.filter;

import br.com.systec.opusfinancial.financial.creditcard.api.filter.FilterCreditCard;
import br.com.systec.opusfinancial.financial.creditcard.impl.entity.CreditCardEntity;
import org.springframework.data.jpa.domain.Specification;

public class CreditCardSpecification {

    private CreditCardSpecification() {}

    public static CreditCardSpecification of() {
        return new CreditCardSpecification();
    }

    public Specification<CreditCardEntity> filter(FilterCreditCard filter) {
        return Specification.unrestricted();
    }
}
