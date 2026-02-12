package org.example.modelo.enums;

public enum OpcionPropiedad {
    VENTA("venta"),
    ALQUILER("alquiler"),
    ALQUILER_OPCION_COMPRA("alquiler con opción a compra"),
    TRASPASO("traspaso");

    private final String valorDb;

    OpcionPropiedad(String valorDb) {
        this.valorDb = valorDb;
    }

    public String getValorDb() { return valorDb; }

    public static OpcionPropiedad desdeString(String texto) {
        for (OpcionPropiedad op : values()) {
            if (op.valorDb.equalsIgnoreCase(texto)) return op;
        }
        throw new IllegalArgumentException("Opción desconocida: " + texto);
    }
}