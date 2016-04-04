package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import java.io.IOException;

/**
 * This class checks the network connection with any URL passed in
 * Calls NetworkCheckResponse.onNetworkCheckSuccess() or
 * NetworkCheckResponse.onNetworkCheckFailure() based on the result.
 *
 * Don't use directly, use it through UtilitiesModel
 *
 * Created by Joshua on 2/21/16.
 */
public class NetworkCheckTask extends FetchTask {

    public NetworkCheckResponse delegate;

    /**
     * Constructor of NetworkCheckTask
     * @param requestURL URL
     */
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
    @Override
    protected void onPostExecute(Object response) {
        boolean success = (boolean) response;
        if (success) {
            delegate.onNetworkCheckSuccess();
        } else {
            delegate.onNetworkCheckFailure();
        }
    }
}