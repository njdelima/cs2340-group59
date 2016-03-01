package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import ga.neerajdelima.themovieapp.R;

/**
 * Created by Neeraj on 2/29/16.
 */
public class MovieSearcherTask extends FetchTask {

    public MovieSearcherResponse delegate;
    private String params;

    public MovieSearcherTask(String params) {
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
        params = "s=" + params;
    }

    @Override
    protected JSONObject doInBackground(Object... args) {
        Log.d("SENDING REQ", "http://www.omdbapi.com/?" + params);
        sendGetData("http://www.omdbapi.com/", params); // get request i.e. http://www.omdbapi.com/?params
        Log.d("HTTP Response", getResponseMessage()); // Should be 'OK'
        JSONObject response = getInputJSON(); // Gets the response from the API
        return response; // gives it to onPostExecute
    }

    @Override
    protected void onPostExecute(Object response) {
        JSONObject serverResponse = (JSONObject) response;
        Log.d("Server response", serverResponse.toString()); // Look through this in the logs
        delegate.onMovieSearchComplete(serverResponse);
    }
}