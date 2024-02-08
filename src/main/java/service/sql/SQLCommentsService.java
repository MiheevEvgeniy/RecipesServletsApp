package service.sql;

import model.Comment;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLCommentsService {

    public Optional<Comment> addComment(Comment comment) {
        Optional<Comment> addedComment = Optional.empty();
        // Запрос в таблицу с комментариями
        String sqlMain = "INSERT INTO comments(recipe_id, author, timestamp, content)" +
                "VALUES(?,?,?,?)";

        // Подключение к бд
        try (Connection connection = Database.connect()) {
            if (connection != null) {
                // Выражения для выполнения запросов
                PreparedStatement statement = connection.prepareStatement(sqlMain, Statement.RETURN_GENERATED_KEYS);

                // Заполнение основного запроса данными
                statement.setLong(1, comment.getRecipeId());
                statement.setString(2, comment.getAuthor());
                statement.setTimestamp(3, Timestamp.valueOf(comment.getTimestamp()));
                statement.setString(4, comment.getContent());
                statement.executeUpdate();

                // Получение добавленных данных
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        addedComment = Optional.of(new Comment.builder()
                                .id(generatedKeys.getLong(1))
                                .recipeId(generatedKeys.getLong(2))
                                .author(generatedKeys.getString(3))
                                .timestamp(generatedKeys.getTimestamp(4).toLocalDateTime())
                                .content(generatedKeys.getString(5))
                                .build());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addedComment;
    }

    public List<Comment> findByRecipeId(Long recipeId) {
        List<Comment> comments = new ArrayList<>();

        String sql = "SELECT c.id, c.recipe_id, c.author, c.timestamp, c.content " +
                "FROM comments as c " +
                "WHERE recipe_id = ?";

        // Подключение к бд
        try (Connection connection = Database.connect()) {
            if (connection != null) {
                // Выражение для выполнения запроса
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                statement.setLong(1, recipeId);

                // Получение данных
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        comments.add(new Comment.builder()
                                .id(result.getLong(1))
                                .recipeId(result.getLong(2))
                                .author(result.getString(3))
                                .timestamp(result.getTimestamp(4).toLocalDateTime())
                                .content(result.getString(5))
                                .build());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
}
