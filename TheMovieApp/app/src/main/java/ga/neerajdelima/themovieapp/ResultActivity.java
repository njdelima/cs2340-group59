package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import ga.neerajdelima.themovieapp.model.RatingsModel;
import ga.neerajdelima.themovieapp.model.network.FetchMovieInfoResponse;
import ga.neerajdelima.themovieapp.model.network.FetchMovieRatingResponse;

/**
 * Class that handles ResultActivity.
 * @author Min Ho Lee
 * @version 1.0
 */
public class ResultActivity extends AppCompatActivity implements FetchMovieInfoResponse, FetchMovieRatingResponse {
    private String result;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
//    private String imgUrl;
    private TextView textView;
//    private ImageView imgView;
    RatingsModel ratingsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        ratingsModel = new RatingsModel();
        ratingsModel.rateMovie("neeraj", "123456789", 3);
        ratingsModel.getMovieRating(this, "123456789"); // look below for onMovieRatingResponse()

        textView = (TextView)findViewById(R.id.resultView);
        Intent intent = getIntent();
<<<<<<< HEAD
        result = "http://www.omdbapi.com/?t=";
        String s = intent.getStringExtra("result");
        StringTokenizer tokens = new StringTokenizer(s);
        String urlFormat = "";
        while (tokens.hasMoreTokens()) {
            urlFormat += tokens.nextToken() + "+";
        }
        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,R.array.rating_score, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), parent.getItemIdAtPosition(position) + " selected", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        urlFormat.substring(0, urlFormat.length() - 2);
        result += urlFormat;
        new JSONTask().execute(result);
=======
        String movieTitle = intent.getStringExtra("result");

        ratingsModel.getMovieInfo(this, movieTitle);
>>>>>>> master
    }

    @Override
    public void onMovieRatingResponse(int totalRating, int ratingCount) {
        Log.d("Total Rating", Integer.toString(totalRating));
        Log.d("Rating Count", Integer.toString(ratingCount));
    }

    @Override
    public void onFetchMovieInfoResponse(String title, String year, String rated,
                                         String released, String runtime,
                                         String genre, String director, String writer,
                                         String actors, String plot, String language,
                                         String country, String awards) {
        String finalOutput = "Title : " + title + "\nYear : "+ year +"\nRated : "+ rated + "\nReleased : "+ released
                + "\nRuntime : " + runtime + "\nGenre : " + genre + "\nDirector : " + director + "\nWriter : "
                + writer + "\nActors : " + actors + "\nPlot : " + plot + "\nLanguage " + language + "\nCountry : "
                + country + "\nAwards : " + awards;

        textView.setText(finalOutput);
    }

}
