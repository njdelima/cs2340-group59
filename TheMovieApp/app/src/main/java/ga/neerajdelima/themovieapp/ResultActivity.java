package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.text.DecimalFormat;

import ga.neerajdelima.themovieapp.model.RatingsModel;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchMovieInfoResponse;
import ga.neerajdelima.themovieapp.model.network.FetchMovieRatingResponse;

/**
 * Class that handles ResultActivity.
 * @author Min Ho Lee
 * @version 1.0
 */
public class ResultActivity extends AppCompatActivity implements FetchMovieInfoResponse, FetchMovieRatingResponse {
    private String result;
    private Spinner spinner;
    private int rating;
    private String imdbID;
    private TextView textView;
    private RatingsModel ratingsModel;
    private TextView totalRatingText;
    private double actualTotalRating;
    private int ratingCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        final UserModel userModel = new UserModel();
        final String loggedInUser = userModel.getLoggedInUsername();
        totalRatingText = (TextView) findViewById(R.id.movieBuzzRating);
        final Intent intent = getIntent();
        final String movieTitle = intent.getStringExtra("result");
        ratingsModel = new RatingsModel();
        ratingsModel.getMovieInfoByTitle(this, movieTitle);
        final Button button = (Button) findViewById(R.id.submitRatingButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RatingBar ratingBar = (RatingBar) findViewById(R.id.movieRatingBar);
                int newRating = (int) ratingBar.getRating();
                if (newRating != 0) {
                    ratingsModel.rateMovie(loggedInUser, imdbID, newRating);
                    ratingsModel.getMovieRating(ResultActivity.this, imdbID);
                    //Toast.makeText(getApplicationContext(), "Submitted", Toast.LENGTH_LONG).show();
                }
            }
        });
       // totalRatingText.setText(actualTotalRating);
    }

    public void back(View view) {
        finish();
    }


    @Override
    public void onMovieRatingResponse(int totalRating, int ratingCount) {
        //totalRatingText.setText(totalRating);
        if (ratingCount == 0) {
            actualTotalRating = 0;
        } else {
            actualTotalRating =  ((double)totalRating / ratingCount);
        }
        this.ratingCount = ratingCount;
        Log.d("Total Rating", Double.toString(actualTotalRating));
        Log.d("Rating Count", Double.toString(ratingCount));
        if (ratingCount == 0) {
            String str = "★ n/a\n(" + ratingCount + ")";
            totalRatingText.setText(str);
        } else {
            DecimalFormat df = new DecimalFormat("#.##");
            String str = "★" + df.format(actualTotalRating) + " / 10\n\t\t\t\t(" + ratingCount + ")";
            totalRatingText.setText(str);
        }
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.movieBuzz);

        if (actualTotalRating > 7) {
            rl.setBackgroundColor(Color.parseColor("#66CC33"));
        } else if (actualTotalRating > 4) {
            rl.setBackgroundColor(Color.parseColor("#FFCC33"));
        } else if (actualTotalRating > 0) {
            rl.setBackgroundColor(Color.parseColor("#FF0000"));
        }
    }
    @Override
    public void onFetchMovieInfoResponse(String title, String year, String rated,
                                  String released, String runtime, String genre,
                                  String director, String writer, String actors,
                                  String plot, String language, String country,
                                  String awards, String poster, String imdbID,
                                  String metascore, String imdbRating, String imdbVotes) {

        TextView nameText = (TextView) findViewById(R.id.movieName);
        nameText.setText(title + " (" + year + ")");

        TextView plotText = (TextView) findViewById(R.id.moviePlot);
        plotText.setText(plot);

        TextView directorText = (TextView) findViewById(R.id.movieDirector);
        String str = "<font color=#ecb540>Director:</font> " + director;
        directorText.setText(Html.fromHtml(str));

        TextView writerText = (TextView) findViewById(R.id.movieWriter);
        str = "<font color=#ecb540>Writers:</font> " + writer;
        writerText.setText(Html.fromHtml(str));

        TextView starsText = (TextView) findViewById(R.id.movieStars);
        str = "<font color=#ecb540>Stars:</font> " + actors;
        starsText.setText(Html.fromHtml(str));


        TextView metascoreText = (TextView) findViewById(R.id.movieMetascore);
        Log.d("METASCORE ERROR", "M= " + metascore);

        if (!metascore.equals("N/A")) {
            if (isNumeric(metascore)) {
                int intMetascore = Integer.parseInt(metascore);
                if (intMetascore > 60) {
                    metascoreText.setBackgroundColor(Color.parseColor("#66CC33"));
                } else if (intMetascore > 39) {
                    metascoreText.setBackgroundColor(Color.parseColor("#FFCC33"));
                } else if (intMetascore > 0) {
                    metascoreText.setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        } else {
            metascoreText.setBackgroundColor(Color.parseColor("#777777"));
        }
        metascoreText.setText(metascore);

        TextView imdbRatingText = (TextView) findViewById(R.id.movieImdbRating);
        if (!imdbRating.equals("N/A")) {
            if (isNumeric(imdbRating)) {
                float fImdbRating = Float.parseFloat(imdbRating);
                if (fImdbRating > 7) {
                    imdbRatingText.setBackgroundColor(Color.parseColor("#66CC33"));
                } else if (fImdbRating > 4) {
                    imdbRatingText.setBackgroundColor(Color.parseColor("#FFCC33"));
                } else if (fImdbRating > 0) {
                    imdbRatingText.setBackgroundColor(Color.parseColor("#FF0000"));
                }
            }
        } else {
            imdbRatingText.setBackgroundColor(Color.parseColor("#777777"));
        }
        imdbRatingText.setText(imdbRating);

        TextView buzzRatingText = (TextView) findViewById(R.id.movieBuzzRating);

        this.imdbID = imdbID;
        ratingsModel.getMovieRating(this, imdbID);
   //     textView.setText(finalOutput);
        new DownloadImageTask((ImageView) findViewById(R.id.movieImage))
                .execute(poster);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
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
            bmImage.setImageBitmap(result);
        }
    }

    private static boolean isNumeric(String str) {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
