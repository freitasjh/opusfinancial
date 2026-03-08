package br.com.systec.opusfinancial.financial.creditcard.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.ObjectNotFoundException;
import br.com.systec.opusfinancial.i18n.I18nTranslate;

import java.io.Serial;

public class CreditCardNotFoundException extends ObjectNotFoundException {
    @Serial
    private static final long serialVersionUID = 2856691985594528223L;

    public CreditCardNotFoundException() {
        super(I18nTranslate.toLocale("credit.card.not.found"));
    }
}
