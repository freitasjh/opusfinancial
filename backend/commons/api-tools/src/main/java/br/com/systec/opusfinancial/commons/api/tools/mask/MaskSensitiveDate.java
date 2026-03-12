package br.com.systec.opusfinancial.commons.api.tools.mask;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = MaskSerializer.class)
public @interface MaskSensitiveDate {
    /**
     * O caractere ou string a ser usado para mascarar.
     */
    String maskChar() default "*";

    /**
     * Quantos caracteres manter visíveis no início.
     */
    int keepStart() default 0;

    /**
     * Quantos caracteres manter visíveis no final.
     */
    int keepEnd() default 4;
}
