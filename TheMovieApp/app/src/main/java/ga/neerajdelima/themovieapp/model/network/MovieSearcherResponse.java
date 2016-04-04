package ga.neerajdelima.themovieapp.model.network;

import org.json.JSONObject;

/**
 * This interface should be implemented by any class that calls
 * RatingsModel.searchForMovie().
 *
 * The result of that call will be passed as params to onMovieSearchComplete()
 *
 *
 * Created by Neeraj on 2/29/16.
 */
public interface MovieSearcherResponse {
    /**
     * Send JSON data to onMovieSearchComplete
     * @param results result of fetched movie
     */
    void onMovieSearchComplete(JSONObject results);
}
