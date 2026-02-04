package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "terrenos")
public class Terreno {
    @Id
    @Column(name = "propiedad_id", nullable = false)
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedade propiedades;

    @Column(name = "edificabilidad", precision = 10, scale = 2)
    private BigDecimal edificabilidad;

    @ColumnDefault("false")
    @Column(name = "urbanizable")
    private Boolean urbanizable;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Propiedade getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(Propiedade propiedades) {
        this.propiedades = propiedades;
    }

    public BigDecimal getEdificabilidad() {
        return edificabilidad;
    }

    public void setEdificabilidad(BigDecimal edificabilidad) {
        this.edificabilidad = edificabilidad;
    }

    public Boolean getUrbanizable() {
        return urbanizable;
    }

    public void setUrbanizable(Boolean urbanizable) {
        this.urbanizable = urbanizable;
    }

}