package com.nullpointexecutioners.buzzfilms.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nullpointexecutioners.buzzfilms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class RecentReleasesFragment extends Fragment {

    private ArrayAdapter<String> mMovieAdapter;
    private ArrayAdapter<String> mDVDAdapter;
    private int mPage;
    public static final String ARG_PAGE = "ARG_PAGE";
    private static final int GET_NUM_ITEMS = 10; //number of movies to get

    /**
     * Constructor for the RecentReleasesFragment
     * @param page fragment is on
     * @return newly created fragment
     */
    public static RecentReleasesFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        RecentReleasesFragment fragment = new RecentReleasesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_releases, container, false);

        switch(mPage) {
            case 1:
                FetchMovies taskMovie = new FetchMovies();
                taskMovie.execute();
                recentMoviesList(view);
                break;
            case 2:
                FetchDVDs taskDVD = new FetchDVDs();
                taskDVD.execute();
                recentDVDsList(view);
                break;
        }
        return view;
    }

    /**
     * to show a list of recent movies
     * @param v a view of a movie
     */
    public void recentMoviesList(View v) {
        ListView mRecentMoviesList = (ListView) v.findViewById(R.id.listview_recent_movies);
        mMovieAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.list_item_film,
                R.id.list_item_film,
                new ArrayList<String>());
        mRecentMoviesList.setAdapter(mMovieAdapter);
    }

    /**
     * to show recent dvd listing
     * @param v a view of movie list
     */
    public void recentDVDsList(View v) {
        ListView mRecentDVDsList = (ListView) v.findViewById(R.id.listview_recent_movies);
        mDVDAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.list_item_film,
                R.id.list_item_film,
                new ArrayList<String>());
        mRecentDVDsList.setAdapter(mDVDAdapter);
    }

    /**
     * Class for Fetching data (JSON) using RottenTomatoes API asynchronously
     */
    public class FetchMovies extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchMovies.class.getSimpleName();

        /**
         * to get data from json
         * @param filmJsonStr string of movies
         * @param num a number
         * @return return a list of strings
         * @throws JSONException throws an exception
         */
        private String[] getDataFromJson(String filmJsonStr, int num)
                throws JSONException {

            JSONObject filmJson = new JSONObject(filmJsonStr);
            JSONArray filmArray = filmJson.getJSONArray("movies");

            String[] resultStrs = new String[filmArray.length()];
            for (int i = 0; i < filmArray.length(); i++) {

                // Get the JSON object representing the title
                JSONObject titleObject = filmArray.getJSONObject(i);
                resultStrs[i] = titleObject.getString("title");
            }

            //Disable logging for debugging TODO re-enable
//            for (String s : resultStrs) {
//                Log.v(LOG_TAG, "Movie: " + s);
//            }
            return resultStrs;

        }

        @Override
        protected String[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnectionMovie = null;
            BufferedReader readerMovie = null;

            // Will contain the raw JSON response as a string.
            String movieJsonString = null;

            try {
                URL urlMovie = new URL("http://api.rottentomatoes.com/api/public/v1.0/" +
                        "lists/movies/in_theaters.json?apikey=vbhetn4chdpudf7mqhckacca");

                // Create the request to RottenTomatoes, and open the connection
                urlConnectionMovie = (HttpURLConnection) urlMovie.openConnection();
                urlConnectionMovie.setRequestMethod("GET");
                urlConnectionMovie.connect();

                // Read the input stream into a String
                InputStream inputStreamMovie = urlConnectionMovie.getInputStream();

                StringBuffer bufferMovie = new StringBuffer();
                if (inputStreamMovie == null) {
                    // Nothing to do.
                    return null;
                }
                readerMovie = new BufferedReader(new InputStreamReader(inputStreamMovie));

                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // bufferMovie for debugging.
                String lineMovie;
                while ((lineMovie = readerMovie.readLine()) != null) {
                    final String newLine = "\n";
                    bufferMovie.append(lineMovie);
                    bufferMovie.append(newLine);
                }

                if (bufferMovie.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonString = bufferMovie.toString();

                //Disable logging for debugging, TODO re-enable this
//                Log.v(LOG_TAG, "Forecast JSON String: " + movieJsonString);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnectionMovie != null) {
                    urlConnectionMovie.disconnect();
                }
                if (readerMovie != null) {
                    try {
                        readerMovie.close();
                    } catch (final IOException e) {
                        Log.e("Movie Fetch", "Error closing stream", e);
                    }
                }
            }

            try {
                return getDataFromJson(movieJsonString, GET_NUM_ITEMS);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                mMovieAdapter.clear();
                for (String movie : result) {
                    mMovieAdapter.add(movie);
                }
            }
        }
    }

    public class FetchDVDs extends AsyncTask<String, Void, String[]> {

        private final String LOG_TAG = FetchDVDs.class.getSimpleName();

        /**
         * to get data from json
         * @param filmJsonStr string from json
         * @param num a number
         * @return a list of string movies
         * @throws JSONException throws an exception
         */
        private String[] getDataFromJson(String filmJsonStr, int num)
                throws JSONException {

            JSONObject filmJson = new JSONObject(filmJsonStr);
            JSONArray filmArray = filmJson.getJSONArray("movies");

            String[] resultStrs = new String[filmArray.length()];
            for (int i = 0; i < filmArray.length(); i++) {

                // Get the JSON object representing the title
                JSONObject titleObject = filmArray.getJSONObject(i);
                resultStrs[i] = titleObject.getString("title");
            }

            //Disable logging for debugging TODO re-enable
//            for (String s : resultStrs) {
//                Log.v(LOG_TAG, "Movie: " + s);
//            }
            return resultStrs;

        }

        @Override
        protected String[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnectionDVD = null;
            BufferedReader readerDVD = null;

            // Will contain the raw JSON response as a string.
            String dvdJsonString = null;

            try {
                URL urlDVD = new URL("http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?apikey=vbhetn4chdpudf7mqhckacca");

                // Create the request to RottenTomatoes, and open the connection
                urlConnectionDVD = (HttpURLConnection) urlDVD.openConnection();
                urlConnectionDVD.setRequestMethod("GET");
                urlConnectionDVD.connect();

                // Read the input stream into a String
                InputStream inputStreamDVD = urlConnectionDVD.getInputStream();

                StringBuffer bufferDVD = new StringBuffer();
                if (inputStreamDVD == null) {
                    // Nothing to do.
                    return null;
                }
                readerDVD = new BufferedReader(new InputStreamReader(inputStreamDVD));

                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // bufferMovie for debugging.
                String lineDVD;
                while ((lineDVD = readerDVD.readLine()) != null) {
                    final String newLine = "\n";
                    bufferDVD.append(lineDVD);
                    bufferDVD.append(newLine);
                }

                if (bufferDVD.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                dvdJsonString = bufferDVD.toString();

                //Disable logging for debugging, TODO re-enable this
//                Log.v(LOG_TAG, "Forecast JSON String: " + movieJsonString);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnectionDVD != null) {
                    urlConnectionDVD.disconnect();
                }
                if (readerDVD != null) {
                    try {
                        readerDVD.close();
                    } catch (final IOException e) {
                        Log.e("DVD Fetch", "Error closing stream", e);
                    }
                }
            }

            try {
                return getDataFromJson(dvdJsonString, GET_NUM_ITEMS);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null) {
                mDVDAdapter.clear();
                for (String dvd : result) {
                    mDVDAdapter.add(dvd);
                }
            }
        }
    }
}
