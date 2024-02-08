package deserializer;

import com.google.gson.Gson;
import model.Rating;
import model.Recipe;

public class RatingDeserializer {
    private final Gson gson;

    public RatingDeserializer() {
        this.gson = new Gson();
    }

    public Rating deserialize(String json) {
        return gson.fromJson(json, Rating.class);
    }
}
