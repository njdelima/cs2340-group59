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
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> master
    /**
     * Send result to onTopMoviesResponse
     * @param results list of top rated movies
     */
<<<<<<< HEAD
    void onTopMoviesResponse(ArrayList<Movie> results);
=======
=======
>>>>>>> master
    void onTopMoviesResponse(List<Movie> results);
>>>>>>> master
}
