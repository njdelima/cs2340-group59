package ga.neerajdelima.themovieapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ga.neerajdelima.themovieapp.model.network.FetchTask;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }
    public void searchClick(View view) {
        new MovieFetcherTask().execute();
    }
    private class MovieFetcherTask extends FetchTask {

        String params;
        public MovieFetcherTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            EditText searchText = (EditText) findViewById(R.id.movie_search);
            String temp = searchText.getText().toString();
            try {
                temp = URLEncoder.encode(temp, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params = "s=" + temp;
        }

        @Override
        protected JSONObject doInBackground(Object... args) {
            sendGetData("http://www.omdbapi.com/", params); // get request i.e. http://www.omdbapi.com/?params
            Log.d("HTTP Response", getResponseMessage()); // Should be 'OK'
            JSONObject response = getInputJSON(); // Gets the response from the API
            return response; // gives it to onPostExecute
        }

        @Override
        protected void onPostExecute(Object response) {
            JSONObject serverResponse = (JSONObject) response;
            Log.d("Server response", serverResponse.toString()); // Look through this in the logs

            ArrayList<String> resultsArray = new ArrayList<String>();

            //Parsing the JSON example
            JSONArray searchResults = null;
            try {
                searchResults = (JSONArray) serverResponse.get("Search");
                for (int i = 0; i < searchResults.length(); i++) {
                    //Toast.makeText(HomeActivity.this, searchResults.getJSONObject(i).get("Title").toString(), Toast.LENGTH_SHORT).show();
                    resultsArray.add(searchResults.getJSONObject(i).get("Title").toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            updateListView(resultsArray.toArray(new String[resultsArray.size()]));
        }

        private void updateListView(String[] results) {
            ListView mListView = (ListView) findViewById(R.id.search_results_list_view);
            ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, results);
            mListView.setAdapter(mArrayAdapter);
        }
    }
}
