package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class process a make admin task
 * Created by komalhirani on 3/14/16.
 */
public class AdminTask extends FetchTask{
    private String username;
    private boolean set;

    public AdminTask(String username, boolean set) {
        super("http://128.61.104.207:2340/api/users/admin.php");
        this.username = username;
        this.set = set;
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            JSONObject data = new JSONObject();
            data.put("username", username);
            data.put("admin", set);
            Log.d("JSON data", data.toString());
            sendPostData(data); // POST the username to the URL.
            Log.d("Checkpoint", "made it past sendpostdata");
            Log.d("response", getResponseMessage());

        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return null;
    }
}
