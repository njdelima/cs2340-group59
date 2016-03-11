package ga.neerajdelima.themovieapp.model.network;

import android.support.annotation.ArrayRes;

import java.util.ArrayList;

import ga.neerajdelima.themovieapp.model.Movie;

/**
 * Created by Joshua on 3/7/16.
 */
public interface FetchTopMoviesResponse {
    public void onTopMoviesResponse(ArrayList<Movie> results);
}
