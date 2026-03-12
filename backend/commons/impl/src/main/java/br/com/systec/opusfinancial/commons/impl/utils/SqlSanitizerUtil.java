package br.com.systec.opusfinancial.commons.impl.utils;


public class SqlSanitizerUtil {

    private SqlSanitizerUtil() {
        // Utility class
    }

    /**
     * Sanitiza uma string para uso em queries LIKE, escapando caracteres especiais SQL.
     * Deve ser usado quando você concatena '%' manualmente ou usa 'contains' no CriteriaBuilder.
     *
     * @param value O valor original do input
     * @return O valor escapado seguro para usar com LIKE
     */
    public static String escapeLike(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        // Escapa backslash primeiro para não escapar os escapes subsequentes
        return value.replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_");
    }

    /**
     * Limpa uma string de input remover espaços extras e caracteres nulos.
     * Use para campos de filtro exato (=) ou ordenação.
     *
     * @param value O valor original
     * @return String limpa ou null se estiver vazia
     */
    public static String sanitize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        // Remove caracteres nulos e faz trim
        String clean = value.replace("\0", "").trim();
        return clean.isEmpty() ? null : clean;
    }
}
