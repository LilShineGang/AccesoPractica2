package org.example.modelo.enums;

public enum TipoVivienda {
    PISO("piso"),
    CHALET("chalet"),
    ADOSADO("adosado");

    private final String valorDb;

    TipoVivienda(String valorDb) {
        this.valorDb = valorDb;
    }

    public String getValorDb() {
        return valorDb;
    }

    // Método estático necesario para que el conversor funcione
    public static TipoVivienda desdeString(String texto) {
        for (TipoVivienda tipo : values()) {
            if (tipo.valorDb.equalsIgnoreCase(texto)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de vivienda desconocido: " + texto);
    }
}