package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by Joshua on 2/21/16.
 */
public abstract class NetworkCheckTask extends FetchTask {

    public NetworkCheckTask(String requestURL) {
        super(requestURL);
    }

    @Override
    protected Boolean doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(3000);
            connection.connect();
            if (getResponseCode() == 200) {
                return true;
            }
        } catch (IOException e) {
            Log.d("IOException", e.getMessage());
        }
        return false;
    }
}