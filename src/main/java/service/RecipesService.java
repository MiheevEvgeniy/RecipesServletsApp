package service;

import model.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipesService {
    private static final Map<Long, Recipe> recipes = new HashMap<>();
    private static long idCounter;

    public Recipe addRecipe(Recipe recipe) {
        recipe.setId(idCounter++);

        recipes.put(recipe.getId(), recipe);
        return recipe;
    }

    public List<Recipe> getAll() {
        return new ArrayList<>(recipes.values());
    }
}
