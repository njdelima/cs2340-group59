package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
    private String major;
    private List<Movie> results;
    /**
     * Constructor of FetchTopMoviesTask
     * @param m major of users
     */
    public FetchTopMoviesTask(String m) {
        super("http://128.61.104.207:2340/api/ratings/top.php");
        this.major = m;
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            final JSONObject data = new JSONObject();
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
        final JSONObject jsonResponse = (JSONObject) response;
        if (jsonResponse == null) {
            delegate.onTopMoviesResponse(null);
        } else {
            Log.d("Topmovies response", jsonResponse.toString());

            final Iterator<String> keys = jsonResponse.keys();

            results = new ArrayList<Movie>();

            while (keys.hasNext()) {
                final String key = (String) keys.next();
                Log.d("Current key", key);

                final JSONObject curRating = (JSONObject) jsonResponse.opt(key);

                String title = curRating.optString("title");
                int totalRating = curRating.optInt("total_rating");
                int ratingCount = curRating.optInt("rating_count");
                String poster = curRating.optString("poster");
                String year = curRating.optString("year");
                String genre = curRating.optString("genre");
                String plot = curRating.optString("plot");

                final Movie movie = new Movie(key, title, year, genre, plot, totalRating, ratingCount, poster);

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
