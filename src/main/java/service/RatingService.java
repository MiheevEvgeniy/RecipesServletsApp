package service;

import model.Rating;

import java.util.HashMap;
import java.util.Map;

public class RatingService {
    private static final Map<Long, Rating> ratings = new HashMap<>();
    private static long ratingIdCounter;

    public Rating addRating(Rating rating, Long recipeId) {
        rating.setId(ratingIdCounter++);
        rating.setRecipeId(recipeId);

        ratings.put(rating.getRecipeId(), rating);
        return rating;
    }

    public Rating getRatingByRecipeId(Long recipeId) {
        return ratings.get(recipeId);
    }
}
