package org.example.entities;

import jakarta.persistence.*;
import org.example.converters.OpcionPropiedadConverter;
import org.example.converters.TipoRecursoConverter;
import org.example.modelo.enums.OpcionPropiedad;
import org.example.modelo.enums.EstadoPropiedad;

import org.example.modelo.enums.TipoRecurso;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "propiedades")
@Inheritance(strategy = InheritanceType.JOINED) // 5.2.

@NamedQueries({
        @NamedQuery(
                name = "Propiedad.buscarPorRangoPrecio",
                query = "SELECT p FROM PropiedadesJPA p WHERE p.precio BETWEEN :minPrecio AND :maxPrecio"
        ),

        @NamedQuery(
                name = "Propiedad.buscarPorEstadoConUbicacion",
                query = "SELECT p FROM PropiedadesJPA p " +
                        "JOIN FETCH p.localidad l " +
                        "JOIN FETCH l.provinciaJPA " +
                        "WHERE p.estado = :estado"
        ),

        @NamedQuery(
                name = "Propiedad.calcularPrecioMedio",
                query = "SELECT AVG(p.precio) FROM PropiedadesJPA p"
        ),

        @NamedQuery(
                name = "Propiedad.buscarRebajadasPorProvincia",
                query = "SELECT p FROM PropiedadesJPA p " +
                        "WHERE p.localidad.provinciaJPA.nombre = :nombreProvincia " +
                        "AND p.precioRebajado < p.precio"
        ),

        @NamedQuery(
                name = "Propiedad.buscarConMultimediaPorEstado",
                query = "SELECT p FROM PropiedadesJPA p " +
                        "JOIN FETCH p.localidad l " +
                        "JOIN FETCH l.provinciaJPA " +
                        "LEFT JOIN FETCH p.multimediaJPA " +
                        "WHERE p.estado = :estado"
        )
})

public class PropiedadesJPA {

    @Column(name = "opcion", columnDefinition = "opcion_propiedad")
    @Convert(converter = OpcionPropiedadConverter.class)
    private OpcionPropiedad operacion;

    public OpcionPropiedad getOperacion() {
        return operacion;
    }

    public void setOperacion(OpcionPropiedad operacion) {
        this.operacion = operacion;
    }

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "metros_cuadrados", nullable = false, precision = 10, scale = 2)
    private BigDecimal metrosCuadrados;

    @Column(name = "direccion")
    private String direccion;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public BigDecimal getMetros() { return metrosCuadrados; }
    public void setMetros(BigDecimal metros) { this.metrosCuadrados = metros; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Set<MultimediaJPA> getMultimedia() { return multimediaJPA; }

    @Column(name = "referencia", nullable = false, length = 20)
    private String referencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localidad_id")
    private LocalidadesJPA localidad;

    @Column(name = "coordenadas", columnDefinition = "point")
    private Object coordenadas;

    @Column(name = "precio_rebajado", precision = 15, scale = 2)
    private BigDecimal precioRebajado;

    @ColumnDefault("'en venta'")
    @Column(name = "estado", columnDefinition = "estado_propiedad")
    private Object estado;


    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @OneToMany
    private Set<MultimediaJPA> multimediaJPA = new LinkedHashSet<>();

}