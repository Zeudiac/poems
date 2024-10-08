package DAOs;

import entities.Poem;
import exceptions.JpaException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class PoemsDAO {
    private static EntityManagerFactory emf;

    public PoemsDAO(EntityManagerFactory emf) {
        PoemsDAO.emf = emf;
    }

    public List<Poem> fetchPoems() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Poem> query = em.createQuery("SELECT p FROM Poem p", Poem.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Poem getPoemById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Poem.class, id);
        } finally {
            em.close();
        }
    }

    public void addPoem(Poem poem) {
        try (EntityManager em = emf.createEntityManager()) {

            //If the poem already exists
            Optional<Poem> existingPoem = findByTitle(poem.getTitle());
            if (existingPoem.isPresent()) {
                System.out.println("Poem with the title '" + poem.getTitle() + "' already exists.");
                return;
            }

            em.getTransaction().begin();

            //Validate that the poem's title is not null
            if (poem.getTitle() == null || poem.getTitle().isEmpty()) {
                throw new JpaException("Poem title cannot be null or empty.");
            }

            //check if actor with the same name already exists
            TypedQuery<Poem> query = em.createQuery(
                    "SELECT p FROM Poem p WHERE LOWER(p.title) = LOWER(:title)", Poem.class
            );

            query.setParameter("title", poem.getTitle());
            List<Poem> existingPoems = query.getResultList();

            if (!existingPoems.isEmpty()) {
                throw new JpaException("A poem with the title '" + poem.getTitle() + "' already exists.");
            }

            em.persist(poem);
            em.getTransaction().commit();


        } catch (Exception e) {
            throw new JpaException("Failed to create poem in the database", e);
        }

    }


    public void updatePoem(Poem poem) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(poem);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public boolean deletePoem(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Poem poem = em.find(Poem.class, id);
            if (poem != null) {
                em.remove(poem);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } finally {
            em.close();
        }
    }
    public Optional<Poem> findByTitle(String title) {

        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Poem> query = em.createQuery(
                    "SELECT p FROM Poem p WHERE LOWER(p.title) = LOWER(:title)", Poem.class);
            query.setParameter("title", title);
            return query.getResultStream().findFirst();
        }
    }
}