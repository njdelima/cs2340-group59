package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import ga.neerajdelima.themovieapp.model.Movie;


/**
 * This class fetches the Top Movies by rating filtered by major
 * and passes them as a sorted Arraylist to FetchTopMoviesResponse.onTopMoviesResponse()
 *
 * Don't use this class directly - use it through RatingsModel.
 *
 * Created by Joshua on 2/29/16.
 */
public class FetchTopMoviesTask extends FetchTask {
    public FetchTopMoviesResponse delegate;
    String major;
    ArrayList<Movie> results;

    public FetchTopMoviesTask(String major) {
        super("http://128.61.104.207:2340/api/ratings/top.php");
        this.major = major;
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            JSONObject data = new JSONObject();
            data.put("major", major);
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
        JSONObject jsonResponse = (JSONObject) response;
        if (jsonResponse == null) {
            delegate.onTopMoviesResponse(null);
        } else {
            Log.d("Topmovies response", jsonResponse.toString());

            Iterator<String> keys = jsonResponse.keys();

            results = new ArrayList<Movie>();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                Log.d("Current key", key);

                JSONObject curRating = (JSONObject) jsonResponse.opt(key);

                Movie movie = new Movie(key, curRating.optString("title"), curRating.optInt("total_rating"), curRating.optInt("rating_count"));

                results.add(movie);
            }
            Log.d("results arraylist", results.toString());
            Collections.sort(results);
            Collections.reverse(results);
            Log.d("sorted results arraylist", results.toString());

            delegate.onTopMoviesResponse(results);
        }
    }
}
