package deserializer;

import com.google.gson.Gson;
import model.Recipe;

public class RecipeDeserializer {
    private final Gson gson;

    public RecipeDeserializer() {
        this.gson = new Gson();
    }

    public Recipe deserialize(String json) {
        return gson.fromJson(json, Recipe.class);
    }
}
