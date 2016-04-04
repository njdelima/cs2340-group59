package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import java.util.ArrayList;

import ga.neerajdelima.themovieapp.model.Movie;
import ga.neerajdelima.themovieapp.model.RatingsModel;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchTopMoviesResponse;

/**
 * Class that handles HomeActivity.
 * @author
 * @version 1.0
 */

public class HomeActivity extends AppCompatActivity implements FetchTopMoviesResponse {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ArrayAdapter<String> ad;
    UserModel userModel;
    RatingsModel ratingsModel;
    ArrayList<Movie> results;
    Spinner spinner;
    String major;
    String[] majors;
    int sp_position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userModel = new UserModel();
        ratingsModel = new RatingsModel();

        mDrawerList = (ListView) findViewById(R.id.navList);
        String[] optsArray = getResources().getStringArray(R.array.navigation_array);
        addDrawerItems(optsArray);
        majors = getResources().getStringArray(R.array.majorsfilter_array);
        spinner = (Spinner) findViewById(R.id.major_spinner);
        ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, majors);
        major = "all";
        sp_position = ad.getPosition(major);
        spinner.setAdapter(ad);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(sp_position);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleNavClick(view);
                // Toast.makeText(HomeActivity.this, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        ratingsModel.getTopMovies(HomeActivity.this, "all");

//        String message = "Logged in as: " + userModel.getLoggedInUsername();
//        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
    }
    /**
     * Method to navigate the user to the selected option.
     * @param view the current view of the main home screen
     */
    private void handleNavClick(View view) {
        String label = ((TextView) view).getText().toString();
        if (label.equals("Logout")) {
            logout();
        }
        if (label.equals("Profile")) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        if (label.equals("Search")){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        if (label.equals("Home")) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
    /**
     * Method to add the different options to the drawer in the navigation bar
     * @param optsArray the array that lists all of the options presented in the navigation bar
     */
    private void addDrawerItems(String[] optsArray) {
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optsArray);
        mDrawerList.setAdapter(mAdapter);
    }
    /**
     * Method that enables the current logged in user to log out from their account
     */
    private void logout() {
        userModel.setLoggedInUser(null);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onTopMoviesResponse(ArrayList<Movie> results) {
        this.results = results; //results is a sorted arraylist
        ArrayList<String> actualResults = new ArrayList<String>();
        ArrayList<String> movieTitles = new ArrayList<String>();
        if (results == null) {
            actualResults.add("No recommendations by " + major + " majors");
        } else {
            for (int i = 0; i < results.size(); i++) {
                actualResults.add(results.get(i).toString());
            }
            for (int i = 0; i < results.size(); i++) {
                movieTitles.add(results.get(i).getTitle());
            }
        }

        updateListView(actualResults.toArray(new String[actualResults.size()]),movieTitles.toArray(new String[movieTitles.size()]));

    }
    public void filter(View view) {
        major = String.valueOf(spinner.getSelectedItem());
        ratingsModel.getTopMovies(HomeActivity.this,major);
    }
    private void updateListView(String[] results, final String[] movieResults) {
        final ListView mListView = (ListView) findViewById(R.id.recommend_list);
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, results);
        mListView.setAdapter(mArrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String actual = movieResults[position];
                //String item = ((TextView) view).getText().toString();
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                Toast.makeText(getBaseContext(), actual, Toast.LENGTH_LONG).show();
                intent.putExtra("result", actual);
                startActivity(intent);
            }
        });
    }
}
