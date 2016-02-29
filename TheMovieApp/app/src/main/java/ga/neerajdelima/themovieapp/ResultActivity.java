package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textView = (TextView)findViewById(R.id.resultView);
        Intent intent = getIntent();
        String result = "http://www.omdbapi.com/?t=" + intent.getStringExtra("result");
        textView.setText(result);
//        HttpURLConnection connection = null;
//        BufferedReader reader = null;
//        try {
//            URL url = new URL(result);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.connect();
//            InputStream stream = connection.getInputStream();
//            reader = new BufferedReader((new InputStreamReader(stream)));
//            StringBuffer buffer = new StringBuffer();
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                buffer.append(line);
//            }
//            String output = buffer.toString();
//            JSONObject jsonObject = new JSONObject(output);
//            String title = jsonObject.optString("Title");
//            int year = Integer.parseInt(jsonObject.optString("Year"));
//            String rated = jsonObject.optString("Rated");
//            String released = jsonObject.optString("Released");
//            String runtime = jsonObject.optString("Runtime");
//            String genre = jsonObject.optString("Genre");
//            String director = jsonObject.optString("Director");
//            String writer = jsonObject.optString("Writer");
//            String actors = jsonObject.optString("Actors");
//            String plot = jsonObject.optString("Plot");
//            String language = jsonObject.optString("Language");
//            String country = jsonObject.optString("Country");
//            String awards = jsonObject.optString("Awards");
//            String finalOutput = "Title : " + title + "\nYear : "+ year +"\nRated : "+ rated + "\nReleased : "+ released
//                    + "\nRuntime : " + runtime + "\nGenre : " + genre + "\nDirector : " + director + "\nWriter : "
//                    + writer + "\nActors : " + actors + "\nPlot : " + plot + "\nLanguage " + language + "\nCountry : "
//                    + country + "\nAwards : " + awards;
//            textView.setText(finalOutput);
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (connection != null)
//                connection.disconnect();
//            try {
//                if (reader != null)
//                    reader.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
