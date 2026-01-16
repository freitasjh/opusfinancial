package br.com.systec.opusfinancial.financial.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.ObjectNotFoundException;
import br.com.systec.opusfinancial.i18n.I18nTranslate;

import java.io.Serial;

public class AccountNotFoundException extends ObjectNotFoundException {
    @Serial
    private static final long serialVersionUID = -793140482917903952L;

    public AccountNotFoundException() {
        super(I18nTranslate.toLocale("account.not.found"));
    }
}
