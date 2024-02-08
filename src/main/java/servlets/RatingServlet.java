package servlets;

import deserializer.RatingDeserializer;
import model.Rating;
import service.RatingService;
import utils.AppUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet("/rating/recipes/*")
public class RatingServlet extends HttpServlet {
    private static final RatingService service = new RatingService();
    private static final RatingDeserializer ratingDeserializer = new RatingDeserializer();
    private static final AppUtils utils = new AppUtils();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        synchronized (this) {
            String[] paths = req.getPathInfo().split("/");
            String pathVar = paths[1];

            try {
                String json = utils.extractJson(req);

                Rating rating = ratingDeserializer.deserialize(json);

                Long recipeId = Long.valueOf(pathVar);

                rating = service.addRating(rating, recipeId);

                if (rating == null) {
                    resp.setStatus(404);
                } else {
                    buildResponse(resp, rating);
                    resp.setStatus(201);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        synchronized (this) {
            String[] paths = req.getPathInfo().split("/");
            String recipeId = paths[1];

            try {
                Long recId = Long.valueOf(recipeId);

                Rating rating = service.getRatingByRecipeId(recId);
                if (rating == null) {
                    resp.setStatus(404);
                } else {
                    buildResponse(resp, rating);
                    resp.setStatus(200);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    private void buildResponse(HttpServletResponse resp, Rating entity) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + entity.getRecipeId() + "</h1>");
        out.println("<h2>" + entity.getRating() + "</h2>");
        out.println("</body></html>");
    }
}
