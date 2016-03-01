package ga.neerajdelima.themovieapp.model;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import ga.neerajdelima.themovieapp.LoginActivity;
import ga.neerajdelima.themovieapp.model.network.NetworkCheckResponse;
import ga.neerajdelima.themovieapp.model.network.NetworkCheckTask;

/**
 * Created by Joshua on 2/29/16.
 */
public class UtilitiesModel {
    public void checkNetworkConnection(Activity activity) {
        NetworkCheckTask networkCheckTask = new NetworkCheckTask("https://google.com");
        networkCheckTask.delegate = (NetworkCheckResponse) activity;
        networkCheckTask.execute();
    }

}