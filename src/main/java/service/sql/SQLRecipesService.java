package service.sql;

import model.Recipe;
import utils.Database;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SQLRecipesService {

    public Optional<Recipe> addRecipe(Recipe recipe) {
        Optional<Recipe> addedRecipe = Optional.empty();
        // Запрос в таблицу с основными данными рецептов
        String sqlMain = "INSERT INTO recipes(title, description)" +
                "VALUES(?,?)";
        // Запрос в таблицу ингредиентов блюда
        String sqlIngredients = "INSERT INTO recipes_ingredients(recipe_id, ingredient_name, ingredient_value)" +
                "VALUES(?,?,?)";

        // Подключение к бд
        try (Connection connection = Database.connect()) {
            if (connection != null) {
                // Выражения для выполнения запросов
                PreparedStatement mainStatement = connection.prepareStatement(sqlMain, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement ingredientsStatement = connection.prepareStatement(sqlIngredients, Statement.RETURN_GENERATED_KEYS);

                // Заполнение основного запроса данными
                mainStatement.setString(1, recipe.getTitle());
                mainStatement.setString(2, recipe.getDescription());
                mainStatement.executeUpdate();

                // Получение добавленных данных
                try (ResultSet generatedKeys = mainStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        addedRecipe = Optional.of(new Recipe
                                .builder()
                                .id(generatedKeys.getLong(1))
                                .title(generatedKeys.getString(2))
                                .description(generatedKeys.getString(3))
                                .build());
                    }
                }
                if (addedRecipe.isPresent()) {
                    // Заполнение запроса ингредиентов данными
                    for (Map.Entry<String, String> entry : recipe.getIngredients().entrySet()) {
                        ingredientsStatement.setLong(1, addedRecipe.get().getId());
                        ingredientsStatement.setString(2, entry.getKey());
                        ingredientsStatement.setString(3, entry.getValue());
                        ingredientsStatement.addBatch();
                    }
                    int[] rowsAffected = ingredientsStatement.executeBatch();

                    // Получение добавленных данных
                    try (ResultSet generatedKeys = ingredientsStatement.getGeneratedKeys()) {
                        Map<String, String> addedIngredients = new HashMap<>();
                        for (int i = 0; i < rowsAffected.length; i++) {
                            if (generatedKeys.next()) {
                                addedIngredients.put(generatedKeys.getString(3), generatedKeys.getString(4));
                            }
                        }
                        addedRecipe.get().setIngredients(addedIngredients);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addedRecipe;
    }

    public List<Recipe> findAll() {
        List<Recipe> recipes = new ArrayList<>();

        String sql = "SELECT r.id, r.title, r.description, " +
                "STRING_AGG(ri.ingredient_name,'|') AS ingredient_names, " +
                "STRING_AGG(ri.ingredient_value, '|') AS ingredient_values " +
                "FROM recipes as r " +
                "LEFT JOIN recipes_ingredients as ri ON r.id = ri.recipe_id " +
                "GROUP BY r.id";

        // Подключение к бд
        try (Connection connection = Database.connect()) {
            if (connection != null) {
                // Выражение для выполнения запроса
                PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                // Получение данных
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        List<String> ingrNames = Arrays.asList(result.getString(4).split("\\|"));
                        List<String> ingrVals = Arrays.asList(result.getString(5).split("\\|"));

                        recipes.add(new Recipe
                                .builder()
                                .id(result.getLong(1))
                                .title(result.getString(2))
                                .description(result.getString(3))
                                .ingredients(
                                        IntStream.range(0, ingrNames.size())
                                                .boxed()
                                                .collect(Collectors.toMap(ingrNames::get, ingrVals::get))
                                )
                                .build());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipes;
    }
}
