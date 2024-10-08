import config.HibernateConfig;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import controller.PoemController;
import DAOs.PoemsDAO;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        }).start(7007);

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("poems");
        PoemsDAO poemsDAO = new PoemsDAO(emf);
        PoemController poemController = new PoemController(emf);

        // Endpoint to get the list of all poems
        app.get("/poems", ctx -> poemController.getAllPoems(ctx));
        // Endpoint to get a specific poem by id
        app.get("/poem/{id}", ctx -> poemController.getPoemById(ctx));
        // Endpoint to update a specific poem by id
        app.put("/poem/{id}", ctx -> poemController.updatePoem(ctx));
    }
}