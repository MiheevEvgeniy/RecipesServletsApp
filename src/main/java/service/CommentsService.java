package service;

import model.Comment;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CommentsService {
    private static final Map<Long, Comment> comments = new HashMap<>();
    private static long commentIdCounter;

    public Comment addComment(Comment comment, Long recipeId) {
        comment.setId(commentIdCounter++);
        comment.setTimestamp(LocalDateTime.now());
        comment.setRecipeId(recipeId);
        comments.put(comment.getRecipeId(), comment);
        return comment;
    }

    public Comment getCommentByRecipeId(Long recipeId) {
        return comments.get(recipeId);
    }
}
