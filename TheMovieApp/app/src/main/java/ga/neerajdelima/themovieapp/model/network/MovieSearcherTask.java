package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * This class searches the omdb API and passes a JSONObject of the results
 * to MovieSearcherResponse.onMovieSearchComplete().
 *
 * Don't use this class directly - use it through RatingsModel.
 *
 * Created by Neeraj on 2/29/16.
 */
public class MovieSearcherTask extends FetchTask {

    private MovieSearcherResponse delegate;
    private String params;
    /**
     * Constructor of MovieSearcherTask
     * @param params movie name
     */

    public MovieSearcherTask(String params) {
        super();
        this.params = params;
    }

    @Override
    protected void onPreExecute() {
        try {
            params = URLEncoder.encode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.d("UnsupportedEncodingException", e.getStackTrace().toString());
        }
        params = "s=" + params;
    }

    @Override
    protected JSONObject doInBackground(Object... args) {
        Log.d("SENDING REQ", "http://www.omdbapi.com/?" + params);
        sendGetData("http://www.omdbapi.com/", params); // get request i.e. http://www.omdbapi.com/?params
        Log.d("HTTP Response", getResponseMessage()); // Should be 'OK'

        return getInputJSON(); // Gets the response from the API
    }

    @Override
    protected void onPostExecute(Object response) {
        JSONObject serverResponse = (JSONObject) response;
        Log.d("Server response", serverResponse.toString()); // Look through this in the logs
        delegate.onMovieSearchComplete(serverResponse);
    }
}