package servlets;

import deserializer.RecipeDeserializer;
import model.Recipe;
import service.RecipesService;
import utils.AppUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(value = "/recipes")
public class RecipesServlet extends HttpServlet {
    private static final RecipesService service = new RecipesService();
    private static final RecipeDeserializer recipeDeserializer = new RecipeDeserializer();
    private static final AppUtils utils = new AppUtils();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        synchronized (this) {
            List<Recipe> recipes = service.getAll();

            buildResponse(resp, recipes);
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        synchronized (this) {
            String json = utils.extractJson(req);

            Recipe entity = recipeDeserializer.deserialize(json);

            entity = service.addRecipe(entity);

            buildResponse(resp, entity);

            resp.setStatus(201);
        }
    }

    private void buildResponse(HttpServletResponse resp, Recipe entity) throws IOException {
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + entity.getTitle() + "</h1>");
        out.println("<h3>" + entity.getDescription() + "</h3>");
        out.println("<p>" + entity.getIngredients() + "</p>");
        out.println("</body></html>");
    }

    private void buildResponse(HttpServletResponse resp, List<Recipe> entities) throws IOException {
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        for (Recipe recipe : entities) {
            out.println("<h1>" + recipe.getTitle() + "</h1>");
            out.println("<h3>" + recipe.getDescription() + "</h3>");
            out.println("<p>" + recipe.getIngredients() + "</p>");
            out.println("<hr>");
        }
        out.println("</body></html>");
    }
}
