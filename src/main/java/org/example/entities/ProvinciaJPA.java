package org.example.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "provincias")
public class ProvinciaJPA {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @OneToMany(mappedBy = "provinciaJPA")
    private Set<LocalidadesJPA> localidades = new LinkedHashSet<>();

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

    public Set<LocalidadesJPA> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(Set<LocalidadesJPA> localidadesJPAS) {
        this.localidades = localidadesJPAS;
    }

}