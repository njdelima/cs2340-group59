package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ga.neerajdelima.themovieapp.model.RatingsModel;
import ga.neerajdelima.themovieapp.model.network.MovieSearcherResponse;

public class SearchActivity extends AppCompatActivity implements MovieSearcherResponse {

    private EditText searchBox;
    private RatingsModel ratingsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ratingsModel = new RatingsModel();

        searchBox = (EditText) findViewById(R.id.movie_search);
        searchBox.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchClick(searchBox);
            }
        });
    }

    /**
     * Fuction that executes the fetcher to fetch movie results when you click the search button
     * @param view the current view of the search movies screen
     */

    public void searchClick(View view) {
        ratingsModel.searchForMovie(this, searchBox.getText().toString());
    }

    @Override
    public void onMovieSearchComplete(JSONObject results) {
        ArrayList<String> resultsArray = new ArrayList<String>();

        //Parsing the JSON example
        JSONArray searchResults = null;
        try {
            searchResults = (JSONArray) results.get("Search");
            for (int i = 0; i < searchResults.length(); i++) {
                //Toast.makeText(HomeActivity.this, searchResults.getJSONObject(i).get("Title").toString(), Toast.LENGTH_SHORT).show();
                resultsArray.add(searchResults.getJSONObject(i).get("Title").toString());
            }
        } catch (JSONException e) {
            //Log.d("JSONException", e.getStackTrace().toString());
            return;
        }
        updateListView(resultsArray.toArray(new String[resultsArray.size()]));
    }

    private void updateListView(String[] results) {
        final ListView mListView = (ListView) findViewById(R.id.search_results_list_view);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, results);
        mListView.setAdapter(mArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView) view).getText().toString();
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                intent.putExtra("result", item);
                startActivity(intent);
            }
        });
    }

}
