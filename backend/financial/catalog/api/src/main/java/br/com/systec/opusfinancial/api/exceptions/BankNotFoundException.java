package br.com.systec.opusfinancial.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.i18n.I18nTranslate;

import java.io.Serial;

public class BankNotFoundException extends BaseException {
    @Serial
    private static final long serialVersionUID = 7384975568238912875L;

    public BankNotFoundException() {
        super(I18nTranslate.toLocale("bank.not.found"));
    }
}
