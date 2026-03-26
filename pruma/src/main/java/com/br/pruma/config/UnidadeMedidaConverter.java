package com.br.pruma.config;

import com.br.pruma.core.enums.UnidadeMedida;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class UnidadeMedidaConverter  implements AttributeConverter<UnidadeMedida, String> {

    @Override
    public String convertToDatabaseColumn(UnidadeMedida attribute) {
        return (attribute != null) ? attribute.name() : null;
    }

    @Override
    public UnidadeMedida convertToEntityAttribute(String dbData) {
        return (dbData != null) ? UnidadeMedida.of(dbData) : null;
    }
}
