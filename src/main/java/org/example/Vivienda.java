package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "viviendas")
public class Vivienda {
    @Id
    @Column(name = "propiedad_id", nullable = false)
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "propiedad_id", nullable = false)
    private Propiedade propiedades;

    @Column(name = "metros_habitables", precision = 10, scale = 2)
    private BigDecimal metrosHabitables;

    @ColumnDefault("0")
    @Column(name = "habitaciones")
    private Integer habitaciones;

    @ColumnDefault("0")
    @Column(name = "aseos")
    private Integer aseos;

    @ColumnDefault("0")
    @Column(name = "\"baños\"")
    private Integer baños;

    @ColumnDefault("false")
    @Column(name = "piscina")
    private Boolean piscina;

    @ColumnDefault("false")
    @Column(name = "aire_acondicionado")
    private Boolean aireAcondicionado;

    @ColumnDefault("false")
    @Column(name = "garaje")
    private Boolean garaje;

    @Column(name = "tipo_vivienda", columnDefinition = "tipo_vivienda_enum not null")
    private Object tipoVivienda;

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

    public BigDecimal getMetrosHabitables() {
        return metrosHabitables;
    }

    public void setMetrosHabitables(BigDecimal metrosHabitables) {
        this.metrosHabitables = metrosHabitables;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Integer getAseos() {
        return aseos;
    }

    public void setAseos(Integer aseos) {
        this.aseos = aseos;
    }

    public Integer getBaños() {
        return baños;
    }

    public void setBaños(Integer baños) {
        this.baños = baños;
    }

    public Boolean getPiscina() {
        return piscina;
    }

    public void setPiscina(Boolean piscina) {
        this.piscina = piscina;
    }

    public Boolean getAireAcondicionado() {
        return aireAcondicionado;
    }

    public void setAireAcondicionado(Boolean aireAcondicionado) {
        this.aireAcondicionado = aireAcondicionado;
    }

    public Boolean getGaraje() {
        return garaje;
    }

    public void setGaraje(Boolean garaje) {
        this.garaje = garaje;
    }

    public Object getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(Object tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

}