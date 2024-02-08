package servlets;

import model.Comment;
import model.Rating;
import deserializer.CommentDeserializer;
import deserializer.RatingDeserializer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/recipes/*")
public class RatingAndCommentsServlet extends HttpServlet {
    private static final List<Comment> comments = new ArrayList<>();
    private static final List<Rating> ratings = new ArrayList<>();
    private static final CommentDeserializer commentDeserializer = new CommentDeserializer();
    private static final RatingDeserializer ratingDeserializer = new RatingDeserializer();
    private static long commentIdCounter;
    private static long ratingIdCounter;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException { // create new
        synchronized (this){
            String[] paths = req.getPathInfo().split("/");
            String pathVar = paths[1];
            String operation = paths[2];

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = req.getReader();
            String json;
            while ((json = reader.readLine()) != null){
                sb.append(json);
                sb.append(System.lineSeparator());
            }
            json = sb.toString();

            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();

            switch (operation) {
                case "comments" -> {
                    Comment comment = commentDeserializer.deserialize(json);
                    comment.setId(commentIdCounter++);
                    comment.setTimestamp(LocalDateTime.now());
                    comment.setRecipeId(Long.valueOf(pathVar));
                    comments.add(comment);
                    out.println("<html><body>");
                    out.println("<h1>" + comment.getAuthor() + "</h1>");
                    out.println("<h2>" + comment.getContent() + "</h2>");
                    out.println("<h3>" + comment.getRecipeId() + "</h3>");
                    out.println("<h3>" + comment.getTimestamp() + "</h3>");
                    out.println("</body></html>");
                    resp.setStatus(201);
                }
                case "rating" -> {
                    Rating rating = ratingDeserializer.deserialize(json);
                    rating.setId(ratingIdCounter++);
                    rating.setRecipeId(Long.valueOf(pathVar));
                    ratings.add(rating);
                    out.println("<html><body>");
                    out.println("<h1>" + rating.getRecipeId() + "</h1>");
                    out.println("<h2>" + rating.getRating() + "</h2>");
                    out.println("</body></html>");
                    resp.setStatus(201);
                }
                default -> {
                    out.println("<html><body>");
                    out.println("<h1> There is no such endpoint </h1>");
                    out.println("</body></html>");
                    resp.setStatus(404);
                }
            }
        }
    }
}
