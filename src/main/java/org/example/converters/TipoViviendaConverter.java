package org.example.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.modelo.enums.TipoVivienda;
import org.postgresql.util.PGobject;
import java.sql.SQLException;

@Converter(autoApply = true)
public class TipoViviendaConverter implements AttributeConverter<TipoVivienda, Object> {

    @Override
    public Object convertToDatabaseColumn(TipoVivienda attribute) {
        if (attribute == null) return null;
        try {
            PGobject pgObj = new PGobject();
            pgObj.setType("tipo_vivienda");
            pgObj.setValue(attribute.getValorDb());
            return pgObj;
        } catch (SQLException e) {
            throw new RuntimeException("Error convirtiendo a Postgres", e);
        }
    }

    @Override
    public TipoVivienda convertToEntityAttribute(Object dbData) {
        if (dbData == null) return null;
        return TipoVivienda.desdeString(dbData.toString());
    }
}