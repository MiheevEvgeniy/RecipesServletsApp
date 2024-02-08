package service;

import exceptions.NotFoundException;
import model.Rating;
import service.sql.SQLRatingService;

import java.util.List;

public class RatingService {
    private static final SQLRatingService sqlService = new SQLRatingService();

    public Rating addRating(Rating rating) {
        return sqlService.addRating(rating)
                .orElseThrow(() -> new NotFoundException("added rating not found"));
    }

    public List<Rating> getRatingByRecipeId(Long recipeId) {
        return sqlService.findByRecipeId(recipeId);
    }
}
