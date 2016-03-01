package ga.neerajdelima.themovieapp.model.network;

import org.json.JSONObject;

/**
 * Created by Neeraj on 2/29/16.
 */
public interface MovieSearcherResponse {
    void onMovieSearchComplete(JSONObject results);
}
