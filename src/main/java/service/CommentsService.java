package service;

import exceptions.NotFoundException;
import model.Comment;
import service.sql.SQLCommentsService;

import java.time.LocalDateTime;
import java.util.List;

public class CommentsService {
    private static final SQLCommentsService sqlService = new SQLCommentsService();

    public Comment addComment(Comment comment) {
        comment.setTimestamp(LocalDateTime.now());
        return sqlService.addComment(comment)
                .orElseThrow(() -> new NotFoundException("added comment not found"));
    }

    public List<Comment> getCommentByRecipeId(Long recipeId) {
        return sqlService.findByRecipeId(recipeId);
    }
}
