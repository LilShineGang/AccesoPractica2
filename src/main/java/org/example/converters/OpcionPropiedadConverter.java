package org.example.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.modelo.enums.OpcionPropiedad;
import org.postgresql.util.PGobject;
import java.sql.SQLException;

@Converter(autoApply = true)
public class OpcionPropiedadConverter implements AttributeConverter<OpcionPropiedad, Object> {

    @Override
    public Object convertToDatabaseColumn(OpcionPropiedad attribute) {
        if (attribute == null) return null;
        try {
            PGobject pgObj = new PGobject();
            pgObj.setType("opcion_propiedad");
            pgObj.setValue(attribute.getValorDb());
            return pgObj;
        } catch (SQLException e) {
            throw new RuntimeException("Error en conversor OpcionPropiedad", e);
        }
    }

    @Override
    public OpcionPropiedad convertToEntityAttribute(Object dbData) {
        if (dbData == null) return null;
        return OpcionPropiedad.desdeString(dbData.toString());
    }
}