package org.example.modelo.enums;

public enum EstadoPropiedad {
    EN_VENTA("en venta"),
    DESACTIVADO("desactivado"),
    VENDIDO("vendido"),
    RESERVADO("reservado");

    private final String valorDb;

    EstadoPropiedad(String valorDb) {
        this.valorDb = valorDb;
    }

    public String getValorDb() { return valorDb; }

    public static EstadoPropiedad desdeString(String texto) {
        for (EstadoPropiedad estado : values()) {
            if (estado.valorDb.equalsIgnoreCase(texto)) return estado;
        }
        throw new IllegalArgumentException("Estado desconocido: " + texto);
    }
}