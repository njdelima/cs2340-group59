package ga.neerajdelima.themovieapp.model;

import android.app.Activity;

import ga.neerajdelima.themovieapp.model.network.FetchMovieInfoResponse;
import ga.neerajdelima.themovieapp.model.network.FetchMovieInfoTask;
import ga.neerajdelima.themovieapp.model.network.FetchMovieRatingResponse;
import ga.neerajdelima.themovieapp.model.network.FetchMovieRatingTask;
import ga.neerajdelima.themovieapp.model.network.FetchTopMoviesResponse;
import ga.neerajdelima.themovieapp.model.network.FetchTopMoviesTask;
import ga.neerajdelima.themovieapp.model.network.StoreMovieRatingTask;
import ga.neerajdelima.themovieapp.model.network.MovieSearcherResponse;
import ga.neerajdelima.themovieapp.model.network.MovieSearcherTask;

/**
 * Created by Neeraj on 2/29/16.
 */
public class RatingsModel {
    /**
     * Search the omdb API
     *
     * IMPORTANT: Any Activity that calls this method needs to implement the
     * MovieSearcherResponse interface and override the onMovieSearchComplete(JSONObject results)
     * method. The results of the search will be passed to that method as its parameter
     *
     * @param activity the class that implements MovieSearcherResponse and defines onMovieSearchComplete().
     * @param searchText the text to search
     */
    public void searchForMovie(Activity activity, String searchText) {
        MovieSearcherTask movieSearcherTask = new MovieSearcherTask(searchText);
        movieSearcherTask.delegate = (MovieSearcherResponse) activity;
        movieSearcherTask.execute();
    }

    /**
     * Store the rating of a movie in the database.
     *
     * @param username the username of the user who is rating the movie
     * @param imdbID the 9-digit imdbID of the movie
     * @param rating the rating, should be 1-10
     */
    public void rateMovie(String username, String imdbID, int rating) {
        new StoreMovieRatingTask(username,imdbID, rating).execute();
    }

    /**
     * Get the totalRating sum and the ratingCount for a movie.
     * So you can calculate the average rating by totalRating/ratingCount
     *
     * IMPORTANT: Any activity that calls this method MUST implement the
     * FetchMovieRatingResponse interface and override the
     * onMovieRatingResponse(int totalRating, int ratingCount) method.
     * The fetched totalRating and ratingCount will be passed to that method as
     * params.
     *
     *
     * @param activity the class that implements FetchMovieRatingResponse and implements
     *                 onMovieRatingResponse().
     * @param imdbID the 9 digit imdbId of the movie in question
     */
    public void getMovieRating(Activity activity, String imdbID) {
        FetchMovieRatingTask fetchMovieRatingTask = new FetchMovieRatingTask(imdbID);
        fetchMovieRatingTask.delegate = (FetchMovieRatingResponse) activity;
        fetchMovieRatingTask.execute();
    }

    /**
     * Get a bunch of information about a movie
     *
     * IMPORTANT: Any activity that calls this method MUST implement
     * the FetchMovieInfoResponse interface and override the
     * onMovieInfoResponse() method
     * All the returned information about the movie will be passed as params
     * to onMovieInfoResponse() method.
     * @param activity activity is to be executed
     * @param title movie title
     */
    public void getMovieInfoByTitle(Activity activity, String title) {
        FetchMovieInfoTask fetchMovieInfoTask = new FetchMovieInfoTask(title);
        fetchMovieInfoTask.delegate = (FetchMovieInfoResponse) activity;
        fetchMovieInfoTask.execute();
    }

    /**
     * Get an ArrayList of movies that have been rated by a particular major.
     * Returns a sorted arraylist of movies.
     *
     * IMPORTANT: Any activity that calls this method MUST implement
     * the FetchTopMoviesResponse interface and override the
     * onTopMoviesResponse() method
     * The returned movies are passed as an arraylist to the onTopMoviesResponse()
     * method
     * @param activity activity is to be executed
     * @param major user's major
     */
    public void getTopMovies(Activity activity, String major) {
        FetchTopMoviesTask fetchTopMoviesTask = new FetchTopMoviesTask(major);
        fetchTopMoviesTask.delegate = (FetchTopMoviesResponse) activity;
        fetchTopMoviesTask.execute();
    }
}
