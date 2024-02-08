package model;

import com.google.gson.annotations.Expose;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
    @Expose
    private Long id;
    @Expose
    private Long recipeId;
    private String author;
    @Expose
    private LocalDateTime timestamp;
    private String content;

    public Comment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id)
                && recipeId.equals(comment.recipeId)
                && Objects.equals(author, comment.author)
                && timestamp.equals(comment.timestamp)
                && Objects.equals(content, comment.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipeId, author, timestamp, content);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", recipeId=" + recipeId +
                ", author='" + author + '\'' +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                '}';
    }
}
