package br.com.systec.opusfinancial.commons.api.utils;


public class MaskUtil {

    private MaskUtil() {
        // Utility class
    }

    /**
     * Verifica se o valor contém caracteres de máscara, indicando que é um valor mascarado
     * retornado anteriormente pela API e não deve ser persistido.
     *
     * @param value O valor a ser verificado
     * @return true se contiver '*' (ou outro char de máscara padrão), false caso contrário.
     */
    public static boolean isMasked(String value) {
        if (value == null || value.isBlank()) {
            return false;
        }
        // Verifica se contém o caractere padrão de máscara '*'
        // Se você usar outros caracteres, adicione aqui.
        return value.contains("*");
    }

    /**
     * Verifica se o valor NÃO está mascarado. Útil para condicionais em Mappers.
     */
    public static boolean isNotMasked(String value) {
        return !isMasked(value);
    }
}
