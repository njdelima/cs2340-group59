package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class processes a ban/unban attempt by an admin
 *
 * Created by komalhirani on 3/14/16.
 */
public class BanTask extends FetchTask{
    private String username;
    private boolean set;

    public BanTask(String username, boolean set) {
        super("http://128.61.104.207:2340/api/users/ban.php");
        this.username = username;
        this.set = set;
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            JSONObject data = new JSONObject();
            data.put("username", username);
            data.put("banned", set);
            Log.d("JSON data", data.toString());
            sendPostData(data); // POST the username to the URL. The DB returns the password for the username
            Log.d("Checkpoint", "made it past sendpostdata");
            Log.d("response", getResponseMessage());


        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return null;
    }

}
