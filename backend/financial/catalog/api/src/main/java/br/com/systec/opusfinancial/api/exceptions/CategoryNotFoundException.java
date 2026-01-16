package br.com.systec.opusfinancial.api.exceptions;

import br.com.systec.opusfinancial.commons.exceptions.BaseException;
import br.com.systec.opusfinancial.i18n.I18nTranslate;

import java.io.Serial;

public class CategoryNotFoundException extends BaseException {
    @Serial
    private static final long serialVersionUID = 9025957241201879437L;

    public CategoryNotFoundException() {
        super(I18nTranslate.toLocale("category.not.found"));
    }
}
