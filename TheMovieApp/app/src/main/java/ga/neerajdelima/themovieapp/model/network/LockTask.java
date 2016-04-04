package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class processes a lock task made by the system after the user has made to many incorrect
 * login attempts
 * Created by komalhirani on 3/14/16.
 */
public class LockTask extends FetchTask {
    private String username;
    private boolean set;
    /**
     * Constructor of LockTask
     * @param u username
     * @param s whether the user is locked
     */

    public LockTask(String u, boolean s) {
        super("http://128.61.104.207:2340/api/users/lock.php");
        this.username = u;
        this.set = s;
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            final JSONObject data = new JSONObject();
            data.put("username", username);
            data.put("locked", set);
            Log.d("JSON data", data.toString());
            sendPostData(data); // POST the username to the URL. The DB returns the password for the username
            Log.d("Checkpoint", "made it past sendpostdata");
            Log.d("response", getResponseMessage());
            //return true;

        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return null;
    }
}
