package org.example.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.modelo.enums.EstadoPropiedad;
import org.postgresql.util.PGobject;
import java.sql.SQLException;

@Converter(autoApply = true)
public class EstadoPropiedadConverter implements AttributeConverter<EstadoPropiedad, Object> {

    @Override
    public Object convertToDatabaseColumn(EstadoPropiedad attribute) {
        if (attribute == null) return null;
        try {
            PGobject pgObj = new PGobject();
            pgObj.setType("estado_propiedad");
            pgObj.setValue(attribute.getValorDb());
            return pgObj;
        } catch (SQLException e) {
            throw new RuntimeException("Error en conversor EstadoPropiedad", e);
        }
    }

    @Override
    public EstadoPropiedad convertToEntityAttribute(Object dbData) {
        if (dbData == null) return null;
        return EstadoPropiedad.desdeString(dbData.toString());
    }
}