package model;

import java.util.Map;
import java.util.Objects;

public class Recipe {

    private Long id;
    private String title;
    private String description;
    private Map<String, String> ingredients;

    public Recipe() {
    }

    public Recipe(builder bd) {
        this.id = bd.id;
        this.title = bd.title;
        this.description = bd.description;
        this.ingredients = bd.ingredients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<String, String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(title, recipe.title)
                && Objects.equals(id, recipe.id)
                && Objects.equals(description, recipe.description)
                && Objects.equals(ingredients, recipe.ingredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, ingredients, id);
    }

    public static class builder {
        private Long id;
        private String title;
        private String description;
        private Map<String, String> ingredients;

        public builder() {
        }

        public builder id(Long id) {
            this.id = id;
            return this;
        }

        public builder title(String title) {
            this.title = title;
            return this;
        }

        public builder description(String description) {
            this.description = description;
            return this;
        }

        public builder ingredients(Map<String, String> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Recipe build() {
            return new Recipe(this);
        }
    }
}
