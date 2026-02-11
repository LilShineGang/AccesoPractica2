package org.example.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(name = "locales")
public class LocalesJPA {
    @Id
    @Column(name = "propiedad_id", nullable = false)
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "propiedad_id", nullable = false)
    private PropiedadesJPA propiedades;

    @ColumnDefault("false")
    @Column(name = "escaparate")
    private Boolean escaparate;

    @ColumnDefault("false")
    @Column(name = "salida_humos")
    private Boolean salidaHumos;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PropiedadesJPA getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(PropiedadesJPA propiedades) {
        this.propiedades = propiedades;
    }

    public Boolean getEscaparate() {
        return escaparate;
    }

    public void setEscaparate(Boolean escaparate) {
        this.escaparate = escaparate;
    }

    public Boolean getSalidaHumos() {
        return salidaHumos;
    }

    public void setSalidaHumos(Boolean salidaHumos) {
        this.salidaHumos = salidaHumos;
    }

}