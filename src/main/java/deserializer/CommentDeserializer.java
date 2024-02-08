package deserializer;

import adapter.LocalDateTimeTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Comment;

import java.time.LocalDateTime;

public class CommentDeserializer {
    private final Gson gson;

    public CommentDeserializer() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
    }

    public Comment deserialize(String json) {
        return gson.fromJson(json, Comment.class);
    }
}
