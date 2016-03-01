package ga.neerajdelima.themovieapp.model.network;

import android.content.Intent;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import ga.neerajdelima.themovieapp.HomeActivity;
import ga.neerajdelima.themovieapp.R;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchTask;
import ga.neerajdelima.themovieapp.model.network.ProcessLoginResponse;

/**
 * This class processes a login attempt
 * Calls ProcessLoginResponse.onProcessLoginSuccess() or
 * ProcessLoginResponse.ononProcessLoginFailure() based on the result.
 *
 * Don't use directly, use it through UserModel
 */

public class ProcessLoginTask extends FetchTask {

    public ProcessLoginResponse delegate;
    private String username;
    private String password;

    public ProcessLoginTask(String username, String password) {
        super("http://128.61.104.207:2340/api/users/fetch.php");
        this.username = username;
        this.password = password;
    }

    @Override
    protected Boolean doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            JSONObject data = new JSONObject();
            data.put("username", username);
            Log.d("JSON data", data.toString());
            sendPostData(data); // POST the username to the URL. The DB returns the password for the username
            Log.d("Checkpoint", "made it past sendpostdata");
            JSONObject response = new JSONObject(getInputString()); // Get the returned password
            String retrievedPassword = response.getString("password"); // parse JSON
            Log.d("RETRIEVED PASSWORD", retrievedPassword);
            return retrievedPassword.equals(password); // CHECK THE PASSWORD

        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return false;
    }

    @Override
    protected void onPostExecute(Object response) {
        boolean success = (boolean) response;
        if (success) {
            delegate.onProcessLoginSuccess(this.username);
        } else {
            delegate.onProcessLoginFailure();
        }
    }
}