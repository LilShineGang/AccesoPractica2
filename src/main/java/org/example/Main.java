package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.LocalidadesJPA;
import org.example.entities.ProvinciaJPA;
import org.example.entities.ViviendaJPA;

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

    public static void main(String[] args) {
        getProvincias();
        String id_Alicante= "95ccb118-c924-4c5f-8961-3fca7c124123";
        updateProvincia(UUID.fromString(id_Alicante), "Madrid");
        altaLocalidad();
        modificarVivienda();
        borrarVivienda();
    }

}