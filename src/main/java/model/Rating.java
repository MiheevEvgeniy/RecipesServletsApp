package model;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Rating {
    @Expose
    private Long id;
    @Expose
    private Long recipeId;
    private RatingEnum rating;

    public Rating() {
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

    public RatingEnum getRating() {
        return rating;
    }

    public void setRating(RatingEnum rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating1 = (Rating) o;
        return Objects.equals(id, rating1.id) && Objects.equals(recipeId, rating1.recipeId) && rating == rating1.rating;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipeId, rating);
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", recipeId=" + recipeId +
                ", rating=" + rating +
                '}';
    }
}
