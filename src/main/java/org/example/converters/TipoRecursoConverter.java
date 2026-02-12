package org.example.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.modelo.enums.TipoRecurso;
import org.postgresql.util.PGobject;
import java.sql.SQLException;

@Converter(autoApply = true)
public class TipoRecursoConverter implements AttributeConverter<TipoRecurso, Object> {

    @Override
    public Object convertToDatabaseColumn(TipoRecurso attribute) {
        if (attribute == null) return null;
        try {
            PGobject pgObj = new PGobject();
            pgObj.setType("tipo_recurso");
            pgObj.setValue(attribute.getValorDb());
            return pgObj;
        } catch (SQLException e) {
            throw new RuntimeException("Error en conversor TipoRecurso", e);
        }
    }

    @Override
    public TipoRecurso convertToEntityAttribute(Object dbData) {
        if (dbData == null) return null;
        return TipoRecurso.desdeString(dbData.toString());
    }
}