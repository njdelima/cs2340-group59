package ga.neerajdelima.themovieapp.model;

import android.app.Activity;

import ga.neerajdelima.themovieapp.model.network.MovieSearcherResponse;
import ga.neerajdelima.themovieapp.model.network.MovieSearcherTask;

/**
 * Created by Neeraj on 2/29/16.
 */
public class RatingsModel {
    public void searchForMovie(Activity activity, String searchText) {
        MovieSearcherTask movieSearcherTask = new MovieSearcherTask(searchText);
        movieSearcherTask.delegate = (MovieSearcherResponse) activity;
        movieSearcherTask.execute();
    }
}
