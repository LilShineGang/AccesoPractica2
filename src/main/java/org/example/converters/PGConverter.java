package org.example.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.modelo.PuntoGeografico;
import org.postgresql.geometric.PGpoint;
import java.sql.SQLException;

@Converter(autoApply = true)
public class PGConverter implements AttributeConverter<PuntoGeografico, Object> {

    @Override
    public Object convertToDatabaseColumn(PuntoGeografico attribute) {
        if (attribute == null) {
            return null;
        }
        return new PGpoint(attribute.getX(), attribute.getY());
    }

    @Override
    public PuntoGeografico convertToEntityAttribute(Object dbData) {
        if (dbData == null) {
            return null;
        }

        if (dbData instanceof PGpoint) {
            PGpoint p = (PGpoint) dbData;
            return new PuntoGeografico(p.x, p.y);
        } else if (dbData instanceof String) {
            try {
                PGpoint p = new PGpoint((String) dbData);
                return new PuntoGeografico(p.x, p.y);
            } catch (SQLException e) {
                throw new RuntimeException("Error parseando punto geográfico", e);
            }
        }
        return null;
    }
}