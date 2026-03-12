package br.com.systec.opusfinancial.financial.creditcard.api.service;

import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCard;
import br.com.systec.opusfinancial.financial.creditcard.api.filter.FilterCreditCard;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CreditCardService {

    CreditCard create(CreditCard creditCard);

    CreditCard update(CreditCard creditCard);

    CreditCard findById(UUID creditCardId);

    Page<CreditCard> findByFilter(FilterCreditCard filter);
}
