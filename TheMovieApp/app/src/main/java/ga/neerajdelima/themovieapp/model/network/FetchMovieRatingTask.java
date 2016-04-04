package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class fetches the totalRating and ratingCount for a movie
 * and passes them to FetchMovieRatingResponse.onMovieRatingResponse()
 *
 * Don't use this class directly - use it through RatingsModel.
 *
 * Created by Joshua on 2/29/16.
 */
public class FetchMovieRatingTask extends FetchTask {
    public FetchMovieRatingResponse delegate;
    private String imdbId;
    /**
     * Constructor of FetchMovieRatingTask
     * @param id imdbID
     */

    public FetchMovieRatingTask(String id) {
        super("http://128.61.104.207:2340/api/ratings/fetch.php");
        this.imdbId = imdbId;
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            final JSONObject data = new JSONObject();
            data.put("id", imdbId);
            Log.d("JSON data", data.toString());
            sendPostData(data); // POST the username to the URL. The DB returns the password for the username
            Log.d("Checkpoint", "made it past sendpostdata");
            Log.d("Response message", getResponseMessage());
            return new JSONObject(getInputString()); // Get the returned JSON;

        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return null;
    }
    @Override
    protected void onPostExecute(Object response) {
        final JSONObject jsonResponse = (JSONObject) response;
        try {
            final int totalRating = Integer.parseInt(jsonResponse.getString("total_rating"));
            final int ratingCount = Integer.parseInt(jsonResponse.getString("ratings_count"));
            delegate.onMovieRatingResponse(totalRating, ratingCount);
        } catch (JSONException e) {
            Log.d("JSONException", e.getStackTrace().toString());
        }
    }
}
