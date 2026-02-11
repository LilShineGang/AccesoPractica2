package org.example.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "localidades")
public class LocalidadesJPA {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "provincia_id")
    private ProvinciaJPA provinciaJPA;

    @OneToMany(mappedBy = "localidad")
    private Set<PropiedadesJPA> propiedadesJPAS = new LinkedHashSet<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ProvinciaJPA getProvincia() {
        return provinciaJPA;
    }

    public void setProvincia(ProvinciaJPA provinciaJPA) {
        this.provinciaJPA = provinciaJPA;
    }

    public Set<PropiedadesJPA> getPropiedades() {
        return propiedadesJPAS;
    }

    public void setPropiedades(Set<PropiedadesJPA> propiedadesJPAS) {
        this.propiedadesJPAS = propiedadesJPAS;
    }

}