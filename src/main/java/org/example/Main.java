package org.example;

import jakarta.persistence.*;
import org.example.entities.*;
import org.example.modelo.enums.EstadoPropiedad;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void getProvincias(){
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            List<ProvinciaJPA> provincias = em.createQuery(
                    "select distinct p from ProvinciaJPA p left join fetch p.localidades",
                    ProvinciaJPA.class
            ).getResultList();

            for (ProvinciaJPA provincia : provincias) {
                System.out.println("Provincia: " + provincia.getNombre());

                for (LocalidadesJPA localidad : provincia.getLocalidades()) {
                    System.out.println("  - " + localidad.getNombre());
                }
            }
        } finally {
            em.close();
            emf.close();
        }

    }

    public static void updateProvincia(UUID provinciaId, String nuevoNombre) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            ProvinciaJPA provincia = em.find(ProvinciaJPA.class, provinciaId);
            if (provincia == null) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("No existe la provincia con id=" + provinciaId);
            }

            provincia.setNombre(nuevoNombre);

            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void altaLocalidad() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            UUID idAlicante = UUID.fromString("95ccb118-c924-4c5f-8961-3fca7c124123");

            ProvinciaJPA provinciaP = em.getReference(ProvinciaJPA.class, idAlicante);

            LocalidadesJPA localidad = new LocalidadesJPA();
            localidad.setNombre("Almoradí");
            localidad.setProvincia(provinciaP);

            em.persist(localidad);

            em.getTransaction().commit();
            System.out.println("Localidad Almoradi creada");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }

    public static void modificarVivienda() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            ViviendaJPA viviendaF = new ViviendaJPA();
            viviendaF.setId(UUID.fromString("083f07e9-74c5-4eb2-b8c1-6b73ff5c42b3"));
            viviendaF.setHabitaciones(2);

            em.merge(viviendaF);

            em.getTransaction().commit();
            System.out.println("Vivienda modificada");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void borrarVivienda() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            UUID id = UUID.fromString("083f07e9-74c5-4eb2-b8c1-6b73ff5c42b3");

            ViviendaJPA viviendaF = em.getReference(ViviendaJPA.class, id);

            em.remove(viviendaF);

            em.getTransaction().commit();
            System.out.println("Vivienda borrada");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void issue() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        List<ProvinciaJPA> provincias = em.createQuery("SELECT p FROM ProvinciaJPA p", ProvinciaJPA.class).getResultList();

        for (ProvinciaJPA provincia : provincias) {
            System.out.println(provincia.getNombre());

            for (LocalidadesJPA l : provincia.getLocalidades()) {
                System.out.println("  - " + l.getNombre());

                for (PropiedadesJPA p : l.getPropiedades()) {
                    System.out.println("    - " + p.getDireccion());

                    System.out.println(p.getMultimedia().size());
                }
            }
        }
        em.close();
        emf.close();
    }

    public static void JoFe() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            String jpql = "SELECT DISTINCT p FROM ProvinciaJPA p " +
                    "LEFT JOIN FETCH p.localidades l " +
                    "LEFT JOIN FETCH l.propiedadesJPAS prop " +
                    "LEFT JOIN FETCH prop.multimediaJPA m";

            List<ProvinciaJPA> provincias = em.createQuery(jpql, ProvinciaJPA.class).getResultList();

            for (ProvinciaJPA p : provincias) {
                System.out.println("Provincia: " + p.getNombre());
                for (LocalidadesJPA l : p.getLocalidades()) {
                    System.out.println(" - Localidad: " + l.getNombre());
                    System.out.println("   * Propiedades cargadas: " + l.getPropiedades().size());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    public static void EnGr() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        try {
            EntityGraph<ProvinciaJPA> graph = em.createEntityGraph(ProvinciaJPA.class);

            Subgraph<LocalidadesJPA> localidades = graph.addSubgraph("localidades");

            Subgraph<PropiedadesJPA> propiedades = localidades.addSubgraph("propiedades");

            propiedades.addAttributeNodes("multimediaJPA");

            List<ProvinciaJPA> provincias = em.createQuery("SELECT p FROM ProvinciaJPA p", ProvinciaJPA.class).setHint("javax.persistence.loadgraph", graph).getResultList();

            for (ProvinciaJPA p : provincias) {
                System.out.println("Provincia: " + p.getNombre());
                for (LocalidadesJPA l : p.getLocalidades()) {
                    System.out.println(" - Localidad: " + l.getNombre());
                    System.out.println("   * Propiedades cargadas: " + l.getPropiedades().size());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

    }

    public static void consultaCG() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();

        String jpql = "SELECT p FROM PropiedadesJPA p";
        List<PropiedadesJPA> lista = em.createQuery(jpql, PropiedadesJPA.class).getResultList();

        for (PropiedadesJPA p : lista) {
            System.out.println("ID: " + p.getId() +
                    "- Precio: " + p.getPrecio() +
                    "- Metros " + p.getMetros());

            if (p instanceof ViviendaJPA) {
                ViviendaJPA v = (ViviendaJPA) p;
                System.out.println("Habitaciones: " + v.getHabitaciones());
            } else if (p instanceof LocalesJPA) {
                LocalesJPA l = (LocalesJPA) p;
                System.out.println("Escaparate: " + l.getEscaparate());
            } else if (p instanceof TerrenoJPA) {
                TerrenoJPA t = (TerrenoJPA) p;
                System.out.println("Urbanizable: "+ t.getUrbanizable());

            }
        }
        em.close();
        emf.close();
    }

    public static void probarConsultasNombradas() {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("default");
        EntityManager em = emf.createEntityManager();


    }

    public static void main(String[] args) {
        getProvincias();
        String id_Alicante= "95ccb118-c924-4c5f-8961-3fca7c124123";
        updateProvincia(UUID.fromString(id_Alicante), "Madrid");
        altaLocalidad();
        modificarVivienda();
        borrarVivienda();
        issue();
        JoFe();
        EnGr();
        consultaCG();
    }

}