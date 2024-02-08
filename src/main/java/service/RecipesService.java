package service;

import exceptions.NotFoundException;
import model.Recipe;
import service.sql.SQLRecipesService;

import java.util.List;

public class RecipesService {
    private static final SQLRecipesService sqlService = new SQLRecipesService();

    public Recipe addRecipe(Recipe recipe) {
        return sqlService.addRecipe(recipe)
                .orElseThrow(() -> new NotFoundException("added recipe not found"));
    }

    public List<Recipe> getAll() {
        return sqlService.findAll();
    }
}
