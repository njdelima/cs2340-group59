package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class process a make admin task
 * Created by komalhirani on 3/14/16.
 */
public class AdminTask extends FetchTask{
    public AdminResponse delegate;
    private String username;
    private int set;

    public AdminTask(String username, int set) {
        super("http://128.61.104.207:2340/api/users/admin.php");
        this.username = username;
        this.set = set;
    }

    @Override
    protected Boolean doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            JSONObject data = new JSONObject();
            data.put("username", username);
            data.put("admin", set);
            Log.d("JSON data", data.toString());
            sendPostData(data); // POST the username to the URL. The DB returns the password for the username
            Log.d("Checkpoint", "made it past sendpostdata");
            return true;

        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return false;
    }
    @Override
    protected void onPostExecute(Object response) {
        boolean success = (boolean) response;
        if (success) {
            delegate.onProcessAdminSuccess(this.username, this.set);
        } else {
            delegate.onProcessAdminFailure();
        }
    }
}
