package ga.neerajdelima.themovieapp.model;

import android.app.Activity;


import ga.neerajdelima.themovieapp.model.network.NetworkCheckResponse;
import ga.neerajdelima.themovieapp.model.network.NetworkCheckTask;

/**
 * Created by Joshua on 2/29/16.
 */
public class UtilitiesModel {
    /**
     * Check network connection
     * @param activity activity to be executed
     */
    public void checkNetworkConnection(Activity activity) {
        NetworkCheckTask networkCheckTask = new NetworkCheckTask("https://google.com");
        networkCheckTask.delegate = (NetworkCheckResponse) activity;
        networkCheckTask.execute();
    }

}