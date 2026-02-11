package org.example.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "multimedia")
public class MultimediaJPA {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "propiedad_id")
    private PropiedadesJPA propiedad;

    @Column(name = "fichero", nullable = false)
    private String fichero;

    @ColumnDefault("'imagen'")
    @Column(name = "tipo", columnDefinition = "tipo_recurso")
    private Object tipo;

    @ColumnDefault("0")
    @Column(name = "orden")
    private Integer orden;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PropiedadesJPA getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(PropiedadesJPA propiedad) {
        this.propiedad = propiedad;
    }

    public String getFichero() {
        return fichero;
    }

    public void setFichero(String fichero) {
        this.fichero = fichero;
    }

    public Object getTipo() {
        return tipo;
    }

    public void setTipo(Object tipo) {
        this.tipo = tipo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

}