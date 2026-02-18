package br.com.systec.opusfinancial.financial.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.ObjectNotFoundException;
import br.com.systec.opusfinancial.i18n.I18nTranslate;

import java.io.Serial;

public class IncomingFinancialNotFoundException extends ObjectNotFoundException {
    @Serial
    private static final long serialVersionUID = 735670772560528874L;

    public IncomingFinancialNotFoundException() {
        super(I18nTranslate.toLocale("receiver.financial.not.found"));
    }
}
