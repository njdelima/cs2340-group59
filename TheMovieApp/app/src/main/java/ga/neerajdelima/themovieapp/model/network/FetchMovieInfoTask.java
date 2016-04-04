package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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
    /**
     * Constructor of FetchMovieInfoTask
     * @param p name of the movie fetched
     */
    public FetchMovieInfoTask(String params) {
        super();
        this.params = params;
    }

    @Override
    protected void onPreExecute() {
        try {
            params = URLEncoder.encode(params, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //Log.d("UnsupportedEncodingException", e.getStackTrace().toString());
            return;
        }
    }

    @Override
    protected Object doInBackground(Object... args) {
        Log.d("SENDING REQ", "http://www.omdbapi.com/?t=" + params);
        sendGetData("http://www.omdbapi.com/?t=", params); // get request i.e. http://www.omdbapi.com/?params
        Log.d("HTTP Response", getResponseMessage()); // Should be 'OK'
        final JSONObject response = getInputJSON(); // Gets the response from the API
        Log.d("Imm json response", response.toString());
        return response; // gives it to onPostExecute
    }

    @Override
    protected void onPostExecute(Object response) {
        final JSONObject jsonObject = (JSONObject) response;

        Log.d("movie info response", jsonObject.toString());

        final String title = jsonObject.optString("Title");
        final String year = jsonObject.optString("Year");
        final String rated = jsonObject.optString("Rated");
        final String released = jsonObject.optString("Released");
        final String runtime = jsonObject.optString("Runtime");
        final String genre = jsonObject.optString("Genre");
        final String director = jsonObject.optString("Director");
        final String writer = jsonObject.optString("Writer");
        final String actors = jsonObject.optString("Actors");
        final String plot = jsonObject.optString("Plot");
        final String language = jsonObject.optString("Language");
        final String country = jsonObject.optString("Country");
        final String awards = jsonObject.optString("Awards");
        final String imdbID = jsonObject.optString("imdbID");
        //                imgUrl = jsonObject.optString("Poster");

        delegate.onFetchMovieInfoResponse(title, year, rated, released, runtime, genre,
                director, writer, actors, plot, language, country, awards, imdbID);
    }
}
