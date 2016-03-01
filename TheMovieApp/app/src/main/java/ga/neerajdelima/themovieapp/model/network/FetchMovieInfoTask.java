package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * This class fetches information about a movie
 *
 * Use with RatingsModel.getMovieInfo()
 *
 * Created by Joshua on 2/29/16.
 */
public class FetchMovieInfoTask extends FetchTask {

    public FetchMovieInfoResponse delegate;
    private String params;

    public FetchMovieInfoTask(String params) {
        super();
        this.params = params;
    }

    @Override
    protected void onPreExecute() {
        try {
            params = URLEncoder.encode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params = "t=" + params;
    }

    @Override
    protected Object doInBackground(Object... args) {
        Log.d("SENDING REQ", "http://www.omdbapi.com/?" + params);
        sendGetData("http://www.omdbapi.com/", params); // get request i.e. http://www.omdbapi.com/?params
        Log.d("HTTP Response", getResponseMessage()); // Should be 'OK'
        JSONObject response = getInputJSON(); // Gets the response from the API
        return response; // gives it to onPostExecute
    }

    @Override
    protected void onPostExecute(Object response) {
        JSONObject jsonObject = (JSONObject) response;

        String title = jsonObject.optString("Title");
        String year = jsonObject.optString("Year");
        String rated = jsonObject.optString("Rated");
        String released = jsonObject.optString("Released");
        String runtime = jsonObject.optString("Runtime");
        String genre = jsonObject.optString("Genre");
        String director = jsonObject.optString("Director");
        String writer = jsonObject.optString("Writer");
        String actors = jsonObject.optString("Actors");
        String plot = jsonObject.optString("Plot");
        String language = jsonObject.optString("Language");
        String country = jsonObject.optString("Country");
        String awards = jsonObject.optString("Awards");
        String imdbID = jsonObject.optString("imdbID");
        //                imgUrl = jsonObject.optString("Poster");

        delegate.onFetchMovieInfoResponse(title, year, rated, released, runtime, genre,
                director, writer, actors, plot, language, country, awards, imdbID);
    }
}