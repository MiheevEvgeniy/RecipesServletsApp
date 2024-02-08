package servlets;

import deserializer.CommentDeserializer;
import model.Comment;
import service.CommentsService;
import utils.AppUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@WebServlet(value = "/comments/recipes/*")
public class CommentsServlet extends HttpServlet {
    private static final CommentDeserializer commentDeserializer = new CommentDeserializer();
    private static final AppUtils utils = new AppUtils();
    private static final CommentsService service = new CommentsService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        synchronized (this) {
            String[] paths = req.getPathInfo().split("/");
            String pathVar = paths[1];

            try {
                String json = utils.extractJson(req);

                Comment comment = commentDeserializer.deserialize(json);

                Long recipeId = Long.valueOf(pathVar);
                comment.setRecipeId(recipeId);

                comment = service.addComment(comment);

                if (comment == null) {
                    resp.setStatus(404);
                } else {
                    buildResponse(resp, comment);
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

                List<Comment> comments = service.getCommentByRecipeId(recId);

                if (comments == null) {
                    resp.setStatus(404);
                } else {
                    buildResponse(resp, comments);
                    resp.setStatus(200);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }

    private void buildResponse(HttpServletResponse resp, Comment entity) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + entity.getAuthor() + "</h1>");
        out.println("<h2>" + entity.getContent() + "</h2>");
        out.println("<h3>" + entity.getRecipeId() + "</h3>");
        out.println("<h3>" + entity.getTimestamp() + "</h3>");
        out.println("</body></html>");
    }

    private void buildResponse(HttpServletResponse resp, List<Comment> entities) throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        for (Comment comment : entities) {
            out.println("<h1>" + comment.getAuthor() + "</h1>");
            out.println("<h2>" + comment.getContent() + "</h2>");
            out.println("<h3>" + comment.getRecipeId() + "</h3>");
            out.println("<h3>" + comment.getTimestamp() + "</h3>");
            out.println("<hr>");
        }
        out.println("</body></html>");
    }
}
