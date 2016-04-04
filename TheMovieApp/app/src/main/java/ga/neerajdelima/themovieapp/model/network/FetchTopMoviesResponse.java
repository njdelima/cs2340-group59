package ga.neerajdelima.themovieapp.model.network;

import java.util.List;

import ga.neerajdelima.themovieapp.model.Movie;

/**
 *
 * This interface should be implemented by any class that calls
 * RatingsModel.getTopMovies().
 *
 * The results of that call will be passed as params to onTopMoviesResponse()
 *
 *
 * Created by Neeraj on 2/29/16.
 */
public interface FetchTopMoviesResponse {
    void onTopMoviesResponse(List<Movie> results);
}
