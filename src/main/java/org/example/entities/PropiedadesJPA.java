package org.example.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "propiedades")
public class PropiedadesJPA {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "referencia", nullable = false, length = 20)
    private String referencia;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localidad_id")
    private LocalidadesJPA localidad;

    @Column(name = "metros_cuadrados", nullable = false, precision = 10, scale = 2)
    private BigDecimal metrosCuadrados;

    @Column(name = "coordenadas", columnDefinition = "point")
    private Object coordenadas;

    @Column(name = "precio", nullable = false, precision = 15, scale = 2)
    private BigDecimal precio;

    @Column(name = "precio_rebajado", precision = 15, scale = 2)
    private BigDecimal precioRebajado;

    @ColumnDefault("'en venta'")
    @Column(name = "estado", columnDefinition = "estado_propiedad")
    private Object estado;

    @Column(name = "opcion", columnDefinition = "opcion_propiedad not null")
    private Object opcion;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @OneToOne(mappedBy = "propiedades")
    private LocalesJPA localesJPA;

    @OneToMany
    private Set<MultimediaJPA> multimediaJPA = new LinkedHashSet<>();

    @OneToOne(mappedBy = "propiedades")
    private TerrenoJPA terrenoJPA;

    @OneToOne(mappedBy = "propiedades")
    private ViviendaJPA viviendaJPA;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalidadesJPA getLocalidad() {
        return localidad;
    }

    public void setLocalidad(LocalidadesJPA localidad) {
        this.localidad = localidad;
    }

    public BigDecimal getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(BigDecimal metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public Object getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(Object coordenadas) {
        this.coordenadas = coordenadas;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getPrecioRebajado() {
        return precioRebajado;
    }

    public void setPrecioRebajado(BigDecimal precioRebajado) {
        this.precioRebajado = precioRebajado;
    }

    public Object getEstado() {
        return estado;
    }

    public void setEstado(Object estado) {
        this.estado = estado;
    }

    public Object getOpcion() {
        return opcion;
    }

    public void setOpcion(Object opcion) {
        this.opcion = opcion;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalesJPA getLocale() {
        return localesJPA;
    }

    public void setLocale(LocalesJPA localesJPA) {
        this.localesJPA = localesJPA;
    }

    public Set<MultimediaJPA> getMultimedia() {
        return multimediaJPA;
    }

    public void setMultimedia(Set<MultimediaJPA> multimediaJPA) {
        this.multimediaJPA = multimediaJPA;
    }

    public TerrenoJPA getTerreno() {
        return terrenoJPA;
    }

    public void setTerreno(TerrenoJPA terrenoJPA) {
        this.terrenoJPA = terrenoJPA;
    }

    public ViviendaJPA getVivienda() {
        return viviendaJPA;
    }

    public void setVivienda(ViviendaJPA viviendaJPA) {
        this.viviendaJPA = viviendaJPA;
    }

}