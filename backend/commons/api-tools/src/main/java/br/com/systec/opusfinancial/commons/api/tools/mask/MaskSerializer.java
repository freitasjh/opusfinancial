package br.com.systec.opusfinancial.commons.api.tools.mask;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Collections;

public class MaskSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private final String maskChar;
    private final int keepStart;
    private final int keepEnd;

    // Construtor padrão necessário para o Jackson
    public MaskSerializer() {
        this("*", 0, 4);
    }

    public MaskSerializer(String maskChar, int keepStart, int keepEnd) {
        this.maskChar = maskChar;
        this.keepStart = keepStart;
        this.keepEnd = keepEnd;
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }

        int length = value.length();
        // Se a string for menor que a soma das partes visíveis, mascara tudo para segurança
        if (length <= (keepStart + keepEnd)) {
            gen.writeString(maskChar.repeat(length));
            return;
        }

        String start = value.substring(0, keepStart);
        String end = value.substring(length - keepEnd);
        int maskLength = length - (keepStart + keepEnd);
        String maskedPart = maskChar.repeat(maskLength);

        gen.writeString(start + maskedPart + end);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (property != null) {
            MaskSensitiveDate maskAnnotation = property.getAnnotation(MaskSensitiveDate.class);
            if (maskAnnotation != null) {
                return new MaskSerializer(maskAnnotation.maskChar(), maskAnnotation.keepStart(), maskAnnotation.keepEnd());
            }
            // Se a anotação estiver na classe ou método getter, tente obter de lá
            maskAnnotation = property.getContextAnnotation(MaskSensitiveDate.class);
            if (maskAnnotation != null) {
                return new MaskSerializer(maskAnnotation.maskChar(), maskAnnotation.keepStart(), maskAnnotation.keepEnd());
            }
        }
        return this; // Retorna instância padrão se não encontrar anotação (fallback)
    }
}
