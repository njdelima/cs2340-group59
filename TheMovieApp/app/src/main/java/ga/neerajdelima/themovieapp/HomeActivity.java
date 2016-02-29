package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ga.neerajdelima.themovieapp.model.User;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchTask;

/**
 * Class that handles HomeActivity.
 * @author
 * @version 1.0
 */

public class HomeActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userModel = new UserModel();
        mDrawerList = (ListView) findViewById(R.id.navList);
        String[] optsArray = getResources().getStringArray(R.array.navigation_array);
        addDrawerItems(optsArray);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleNavClick(view);
                // Toast.makeText(HomeActivity.this, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        String message = "Logged in as: " + userModel.getLoggedInUsername();
        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();

        new MovieFetcherTask("s=Dead").execute();
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

    /*
     * Example call to open movie db API.
     */
    private class MovieFetcherTask extends FetchTask {

        String params;
        public MovieFetcherTask(String params) {
            super();
            this.params = params;
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

            //Parsing the JSON example
            JSONArray searchResults = null;
            try {
                searchResults = (JSONArray) serverResponse.get("Search");
                for (int i = 0; i < searchResults.length(); i++) {
                    Toast.makeText(HomeActivity.this, searchResults.getJSONObject(i).get("Title").toString(), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    }

}
