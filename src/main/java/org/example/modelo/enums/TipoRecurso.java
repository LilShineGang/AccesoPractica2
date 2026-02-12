package org.example.modelo.enums;

public enum TipoRecurso {
    FOTO("foto"),
    VIDEO("video"),
    PLANO("plano"),
    TOUR_VIRTUAL("tour_virtual");

    private final String valorDb;

    TipoRecurso(String valorDb) {
        this.valorDb = valorDb;
    }

    public String getValorDb() { return valorDb; }

    public static TipoRecurso desdeString(String texto) {
        for (TipoRecurso tipo : values()) {
            if (tipo.valorDb.equalsIgnoreCase(texto)) return tipo;
        }
        throw new IllegalArgumentException("Tipo de recurso desconocido: " + texto);
    }
}
