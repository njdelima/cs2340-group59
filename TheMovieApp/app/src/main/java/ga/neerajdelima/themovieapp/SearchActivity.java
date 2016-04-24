package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
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
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.MovieSearcherResponse;

public class SearchActivity extends AppCompatActivity implements MovieSearcherResponse {

    private EditText searchBox;
    private RatingsModel ratingsModel;
    private UserModel userModel;

    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        /* Action Bar styling */
        getSupportActionBar().setTitle(Html.fromHtml("<font color=#ecb540>" + getSupportActionBar().getTitle() + "</font>"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mDrawerList = (ListView) findViewById(R.id.navList);
        final String[] optsArray = getResources().getStringArray(R.array.navigation_array);
        addDrawerItems(optsArray);

        ratingsModel = new RatingsModel();
        userModel = new UserModel();

        searchBox = (EditText) findViewById(R.id.movie_search);
        searchBox.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchClick(searchBox);
            }
        });

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleNavClick(view);
                // Toast.makeText(HomeActivity.this, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        setupDrawer();
    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    /**
     * Method to navigate the user to the selected option.
     * @param view the current view of the main home screen
     */
    private void handleNavClick(View view) {
        final String label = ((TextView) view).getText().toString();
        if ("Logout".equals(label)) {
            logout();
        }
        if ("Profile".equals(label)) {
            final Intent intent = new Intent(this, ViewProfileActivity.class);
            startActivity(intent);
        }
        if ("Search".equals(label)){
            final Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        if ("Home".equals(label)) {
            final Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Method to add the different options to the drawer in the navigation bar
     * @param optsArray the array that lists all of the options presented in the navigation bar
     */
    private void addDrawerItems(String[] optsArray) {
        final ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optsArray);
        mDrawerList.setAdapter(mAdapter);
    }
    /**
     * Method that enables the current logged in user to log out from their account
     */
    private void logout() {
        userModel.setLoggedInUser(null);
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Fuction that executes the fetcher to fetch movie results when you click the search button
     * @param view the current view of the search movies screen
     */

    public void searchClick(View view) {
        final String searchText = searchBox.getText().toString();

        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    if (searchText.equals(searchBox.getText().toString())) {
                        ratingsModel.searchForMovie(SearchActivity.this, searchText);
                    }
                }
            },
        300);
    }

    @Override
    public void onMovieSearchComplete(JSONObject results) {
        final ArrayList<String> resultsArray = new ArrayList<String>();

        //Parsing the JSON example
        JSONArray searchResults = null;
        try {
            searchResults = (JSONArray) results.get("Search");
            for (int i = 0; i < searchResults.length(); i++) {
                //Toast.makeText(HomeActivity.this, searchResults.getJSONObject(i).get("Title").toString(), Toast.LENGTH_SHORT).show();
                String str = searchResults.getJSONObject(i).get("Title").toString() + " (";
                str += searchResults.getJSONObject(i).get("Year").toString() + ")";
                resultsArray.add(str);
            }
        } catch (JSONException e) {
            //Log.d("JSONException", e.getStackTrace().toString());
            return;
        }
        updateListView(resultsArray.toArray(new String[resultsArray.size()]));
    }
    /**
     * Update list of fetched movie
     * @param results list of fetched movies
     */
    private void updateListView(String[] results) {
        final ListView mListView = (ListView) findViewById(R.id.search_results_list_view);
        final ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, results);
        mListView.setAdapter(mArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                String item = ((TextView) view).getText().toString();
                item = item.substring(0, item.length() - 7);
                final Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                intent.putExtra("result", item);
                startActivity(intent);
            }
        });
    }

}
