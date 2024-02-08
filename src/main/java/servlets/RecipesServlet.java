package servlets;

import model.Recipe;
import deserializer.RecipeDeserializer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/recipes")
public class RecipesServlet extends HttpServlet {

    private static final List<Recipe> recipes = new ArrayList<>();
    private static final RecipeDeserializer recipeDeserializer = new RecipeDeserializer();
    private static long idCounter;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException { // get all
        synchronized (this){
            resp.setContentType("text/html");

            PrintWriter out = resp.getWriter();
            out.println("<html><body>");
            for (Recipe recipe :recipes) {
                out.println("<h1>" + recipe.getTitle() + "</h1>");
                out.println("<h3>" + recipe.getDescription() + "</h3>");
                out.println("<p>" + recipe.getIngredients() + "</p>");
                out.println("<hr>");
            }
            out.println("</body></html>");
            resp.setStatus(200);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException { // create new
        synchronized (this){
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String json;
            while ((json = reader.readLine()) != null){
                sb.append(json);
                sb.append(System.lineSeparator());
            }
            json = sb.toString();

            Recipe entity = recipeDeserializer.deserialize(json);

            entity.setId(idCounter++);

            recipes.add(entity);

            resp.setContentType("text/html");

            PrintWriter out = resp.getWriter();
            out.println("<html><body>");
            out.println("<h1>" + entity.getTitle() + "</h1>");
            out.println("<h3>" + entity.getDescription() + "</h3>");
            out.println("<p>" + entity.getIngredients() + "</p>");
            out.println("</body></html>");
            resp.setStatus(201);
        }
    }

}
