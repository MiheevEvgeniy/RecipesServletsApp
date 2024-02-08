package service.sql;

import model.Rating;
import model.RatingEnum;
import utils.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLRatingService {

    public Optional<Rating> addRating(Rating rating) {
        Optional<Rating> addedRating = Optional.empty();
        // Запрос в таблицу с рейтингом
        String sqlMain = "INSERT INTO rating(recipe_id, rating)" +
                "VALUES(?,?)";

        // Подключение к бд
        try (Connection connection = Database.connect()) {
            if (connection != null) {
                // Выражения для выполнения запросов
                PreparedStatement statement = connection.prepareStatement(sqlMain, Statement.RETURN_GENERATED_KEYS);

                // Заполнение основного запроса данными
                statement.setLong(1, rating.getRecipeId());
                statement.setString(2, rating.getRating().name());
                statement.executeUpdate();

                // Получение добавленных данных
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        addedRating = Optional.of(new Rating.builder()
                                .id(generatedKeys.getLong(1))
                                .recipeId(generatedKeys.getLong(2))
                                .rating(RatingEnum.valueOf(generatedKeys.getString(3)))
                                .build());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addedRating;
    }

    public List<Rating> findByRecipeId(Long recipeId) {
        List<Rating> ratings = new ArrayList<>();

        String sql = "SELECT r.id, r.recipe_id, r.rating " +
                "FROM rating as r " +
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
                        ratings.add(new Rating.builder()
                                .id(result.getLong(1))
                                .recipeId(result.getLong(2))
                                .rating(RatingEnum.valueOf(result.getString(3)))
                                .build());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratings;
    }
}
