package controller;

import DAOs.PoemsDAO;
import entities.Poem;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PoemController {
    private static PoemsDAO poemsDAO;

    public PoemController(EntityManagerFactory emf) {
        poemsDAO = new PoemsDAO(emf);
    }

    public static void getAllPoems(Context ctx) {
        List<Poem> poems = poemsDAO.fetchPoems();
        ctx.json(poems);
    }

    public static void addPoem(Context ctx) {
        Poem poem = ctx.bodyAsClass(Poem.class);
        poemsDAO.addPoem(poem);
        ctx.status(201); // Status 201: Created
    }

    public static void updatePoem(Context ctx) {
        Poem poem = ctx.bodyAsClass(Poem.class);
        poemsDAO.updatePoem(poem);
        ctx.status(200); // Status 200: OK
    }

    public static void getPoemById(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Poem poem = poemsDAO.getPoemById(id);
        if (poem != null) {
            ctx.status(200).json(poem); // Status 200: OK
        } else {
            ctx.status(404); // Status 404: Not Found
        }
    }

    public static void addPoemById(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        Poem poem = ctx.bodyAsClass(Poem.class);
        poem.setId(id);
        poemsDAO.addPoem(poem);
        ctx.status(201); // Status 201: Created
    }

    public static void deletePoemById(Context ctx) {
        Long id = Long.parseLong(ctx.pathParam("id"));
        if (poemsDAO.deletePoem(id)) {
            ctx.status(200); // Status 200: OK
        } else {
            ctx.status(404); // Status 404: Not Found
        }
    }
}