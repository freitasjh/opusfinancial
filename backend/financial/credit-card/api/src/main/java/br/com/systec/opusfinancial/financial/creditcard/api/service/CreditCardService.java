package br.com.systec.opusfinancial.financial.creditcard.api.service;

import br.com.systec.opusfinancial.financial.creditcard.api.domain.CreditCardVO;
import br.com.systec.opusfinancial.financial.creditcard.api.filter.FilterCreditCard;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface CreditCardService {

    CreditCardVO create(CreditCardVO creditCard);

    CreditCardVO findById(UUID creditCardId);

    Page<CreditCardVO> findByFilter(FilterCreditCard filter);
}
