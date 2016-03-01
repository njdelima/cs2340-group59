package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

/**
 * Class that handles ResultActivity.
 * @author Min Ho Lee
 * @version 1.0
 */
public class ResultActivity extends AppCompatActivity {
    private String result;
//    private String imgUrl;
    private TextView textView;
//    private ImageView imgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView = (TextView)findViewById(R.id.resultView);
        Intent intent = getIntent();
        result = "http://www.omdbapi.com/?t=";
        String s = intent.getStringExtra("result");
        StringTokenizer tokens = new StringTokenizer(s);
        String urlFormat = "";
        while (tokens.hasMoreTokens()) {
            urlFormat += tokens.nextToken() + "+";
        }
        urlFormat.substring(0, urlFormat.length() - 2);
        result += urlFormat;
        new JSONTask().execute(result);
    }
    /**
     * Class that shows the result of the search
     */
    public class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader((new InputStreamReader(stream)));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String output = buffer.toString();
                JSONObject jsonObject = new JSONObject(output);
                String title = jsonObject.optString("Title");
                String year = jsonObject.optString("Year");
                String rated = jsonObject.optString("Rated");
                String released = jsonObject.optString("Released");
                String runtime = jsonObject.optString("Runtime");
                String genre = jsonObject.optString("Genre");
                String director = jsonObject.optString("Director");
                String writer = jsonObject.optString("Writer");
                String actors = jsonObject.optString("Actors");
                String plot = jsonObject.optString("Plot");
                String language = jsonObject.optString("Language");
                String country = jsonObject.optString("Country");
                String awards = jsonObject.optString("Awards");
//                imgUrl = jsonObject.optString("Poster");
                String finalOutput = "Title : " + title + "\nYear : "+ year +"\nRated : "+ rated + "\nReleased : "+ released
                        + "\nRuntime : " + runtime + "\nGenre : " + genre + "\nDirector : " + director + "\nWriter : "
                        + writer + "\nActors : " + actors + "\nPlot : " + plot + "\nLanguage " + language + "\nCountry : "
                        + country + "\nAwards : " + awards;
                return finalOutput;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null)
                    connection.disconnect();
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
        }

    }
}
