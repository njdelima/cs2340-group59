package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This task stores a username, imdbId and rating in the database
 *
 * Use with ratingsModel
 *
 * Created by Joshua on 2/29/16.
 */
public class StoreMovieRatingTask extends FetchTask {
    String username;
    String imdbId;
    int rating;

    /**
     * Constructor of StoreMovieRatingTask
     * @param username username
     * @param imdbId imdbID
     * @param rating rating of movie
     */
    public StoreMovieRatingTask(String username, String imdbId, int rating) {
        super("http://128.61.104.207:2340/api/ratings/add.php");
        this.username = username;
        this.imdbId = imdbId;
        this.rating = rating;
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            JSONObject data = new JSONObject();
            data.put("username", username);
            data.put("id", imdbId);
            data.put("rating", rating);
            Log.d("JSON data", data.toString());
            sendPostData(data); // POST the username to the URL. The DB returns the password for the username
            Log.d("Checkpoint", "made it past sendpostdata");
            Log.d("Response message", getResponseMessage());
        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return null;
    }
}
