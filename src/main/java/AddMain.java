import DAOs.PoemsDAO;
import config.HibernateConfig;
import controller.PoemController;
import entities.Poem;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManagerFactory;

public class AddMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("poems");
        PoemsDAO poemsDAO = new PoemsDAO(emf);
        PoemController poemController = new PoemController(emf);

        poemsDAO.addPoem(new Poem(null,"The Road Not Taken", "Robert Frost","Snowflakes gently fall,\n" +
                "Wrapping earth in quiet white,\n" +
                "Peace in winter’s grip."));
        poemsDAO.addPoem(new Poem(null,"The Road Not Take2n", "Robert Frost","Snowflakes gently fall,\n" +
                "Wrapping earth in quiet white,\n" +
                "Peace in winter’s grip."));
        poemsDAO.addPoem(new Poem(null,"The Road4 Not Taken", "Robert Frost","Snowflakes gently fall,\n" +
                "Wrapping earth in quiet white,\n" +
                "Peace in winter’s grip."));




    }
}