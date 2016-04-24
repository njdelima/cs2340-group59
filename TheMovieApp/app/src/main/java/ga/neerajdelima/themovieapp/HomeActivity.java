package ga.neerajdelima.themovieapp;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ga.neerajdelima.themovieapp.model.Movie;
import ga.neerajdelima.themovieapp.model.RatingsModel;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchTopMoviesResponse;

/**
 * Class that handles HomeActivity.
 * @author Neeraj Delima
 * @version 1.0
 */

public class HomeActivity extends AppCompatActivity implements FetchTopMoviesResponse {
    private UserModel userModel;
    private RatingsModel ratingsModel;
    private Spinner spinner;
    private String major;

    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        userModel = new UserModel();
        ratingsModel = new RatingsModel();

        /* Styling the ActionBar */
        getSupportActionBar().setTitle(Html.fromHtml("<font color=#ecb540>" + getSupportActionBar().getTitle() + "</font>"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mDrawerList = (ListView) findViewById(R.id.navList);
        final String[] optsArray = getResources().getStringArray(R.array.navigation_array);
        addDrawerItems(optsArray);

        final String[] majors = getResources().getStringArray(R.array.majorsfilter_array);
        spinner = (Spinner) findViewById(R.id.major_spinner);
        final ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, majors);
        major = "all";
        final int spPosition = ad.getPosition(major);
        spinner.setAdapter(ad);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(spPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filter(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ratingsModel.getTopMovies(HomeActivity.this, "all");

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleNavClick(view);
                // Toast.makeText(HomeActivity.this, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        setupDrawer();
//        String message = "Logged in as: " + userModel.getLoggedInUsername();
//        Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
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
    @Override
    public void onTopMoviesResponse(List<Movie> results) {
        if (results == null) {
            results = new ArrayList<>();
        }
        updateListView((ArrayList) results);
    }
    /**
     * Filter list
     * @param view view
     */
    public void filter(View view) {
        major = String.valueOf(spinner.getSelectedItem());
        ratingsModel.getTopMovies(HomeActivity.this, major);
    }

    private void updateListView(final ArrayList<Movie> movies) {
        ListView mListView = (ListView) findViewById(R.id.recommend_list);
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        mListView.setAdapter(movieAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movieTitle = movies.get(position).getTitle();
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("result", movieTitle);
                startActivity(intent);
            }
        });
    }

    private class MovieAdapter extends ArrayAdapter<Movie> {
        HashMap<String, Bitmap> images;

        public MovieAdapter(Context context, ArrayList<Movie> movies) {
            super(context, 0, movies);
            images = new HashMap<>();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Movie movie = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie, parent, false);
            }

            ImageView movieImage = (ImageView) convertView.findViewById(R.id.listItemImage);
            TextView movieName = (TextView) convertView.findViewById(R.id.listItemMovieName);
            TextView movieYear = (TextView) convertView.findViewById(R.id.listItemMovieYearRating);
            TextView moviePlot = (TextView) convertView.findViewById(R.id.listItemMoviePlot);
            TextView movieRating = (TextView) convertView.findViewById(R.id.listItemMovieRating);

            if (images.containsKey(movie.getImdbID())) {
                movieImage.setImageBitmap(images.get(movie.getImdbID()));
            } else {
                new DownloadImageTask(movieImage, movie.getImdbID()).execute(movie.getPoster());
            }
            movieName.setText(movie.getTitle());
            movieYear.setText(movie.getYear() + " \u2022 " + movie.getGenre());
            moviePlot.setText(movie.getPlot().substring(0,50) + " ...");


            DecimalFormat df = new DecimalFormat("#.#");

            double rating = ((double)movie.getTotalRating()) / movie.getRatingCount();
            movieRating.setText("★ " + df.format(rating));

            return convertView;
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;
            String imdbID;

            public DownloadImageTask(ImageView bmImage, String imdbID) {
                this.bmImage = bmImage;
                this.imdbID = imdbID;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                images.put(imdbID, result);
                bmImage.setImageBitmap(result);
            }
        }
    }
}
