package ga.neerajdelima.themovieapp.model.network;

/**
 *
 * This interface should be implemented by any class that calls
 * RatingsModel.getMovieRating().
 *
 * The results of that call will be passed as params to onMovieRatingResponse()
 *
 *
 * Created by Neeraj on 2/29/16.
 */
public interface FetchMovieRatingResponse {

    void onMovieRatingResponse(int totalRating, int ratingCount);
}
