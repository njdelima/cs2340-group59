package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        TextView textView = (TextView)findViewById(R.id.result_display);
        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        try {
            JSONObject jsonObject = new JSONObject(result);
            String title = jsonObject.optString("Title").toString();
            int year = Integer.parseInt(jsonObject.optString("Year").toString());
            String rated = jsonObject.optString("Rated").toString();
            String released = jsonObject.optString("Released").toString();
            String runtime = jsonObject.optString("Runtime").toString();
            String genre = jsonObject.optString("Genre").toString();
            String director = jsonObject.optString("Director").toString();
            String writer = jsonObject.optString("Writer").toString();
            String actors = jsonObject.optString("Actors").toString();
            String plot = jsonObject.optString("Plot").toString();
            String language = jsonObject.optString("Language").toString();
            String country = jsonObject.optString("Country").toString();
            String awards = jsonObject.optString("Awards").toString();
            String output = "Title : " + title + "\nYear : "+ year +"\nRated : "+ rated + "\nReleased : "+ released
                    + "\nRuntime : " + runtime + "\nGenre : " + genre + "\nDirector : " + director + "\nWriter : "
                    + writer + "\nActors : " + actors + "\nPlot : " + plot + "\nLanguage " + language + "\nCountry : "
                    + country + "\nAwards : " + awards;
            textView.setText(output);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}