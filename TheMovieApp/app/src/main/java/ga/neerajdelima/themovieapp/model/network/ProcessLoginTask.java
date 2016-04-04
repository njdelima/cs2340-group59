package ga.neerajdelima.themovieapp.model.network;


import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;


/**
 * This class processes a login attempt
 * Calls ProcessLoginResponse.onProcessLoginSuccess() or
 * ProcessLoginResponse.ononProcessLoginFailure() based on the result.
 *
 * Don't use directly, use it through UserModel
 */

public class ProcessLoginTask extends FetchTask {

    public ProcessLoginResponse delegate;
    private int isAdmin;
    private int isBan;
    private int isLocked;
    private String username;
    private String password;

    public ProcessLoginTask(String username, String password) {
        super("http://128.61.104.207:2340/api/users/fetch.php");
        this.username = username;
        this.password = password;
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            JSONObject data = new JSONObject();
            data.put("username", username);
            Log.d("JSON data", data.toString());
            sendPostData(data); // POST the username to the URL. The DB returns the password for the username
            Log.d("Checkpoint", "made it past sendpostdata");
            JSONObject response = new JSONObject(getInputString()); // Get the returned password
            String retrievedPassword = response.getString("password"); // parse JSON
            String retrievedIsAdmin = response.getString("admin");
            String retrievedIsLocked = response.getString("locked");
            String retrievedIsBan = response.getString("banned");
            isBan = Integer.parseInt(retrievedIsBan);
            isLocked = Integer.parseInt(retrievedIsLocked);
            isAdmin = Integer.parseInt(retrievedIsAdmin);
            Log.d("RETRIEVED PASSWORD", retrievedPassword);
            if (isAdmin == 1) {
                return 4;
            } else if (isBan == 1) {
                return 3;
            } else if (isLocked == 1) {
                return 2;
            } if (retrievedPassword.equals(password)) { // CHECK THE PASSWORD
                return 1;
            }

        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return 0;
    }

    @Override
    protected void onPostExecute(Object response) {
        int success = (Integer) response;
        if (success == 4) {
            delegate.onProcessLoginAsAdmin(this.username);
        } else if (success == 3) {
            delegate.onProcessLoginBan();
        } else if (success == 2) {
            delegate.onProcessLoginLocked();
        }else if (success == 1) {
            delegate.onProcessLoginSuccess(this.username);
        } else {
            delegate.onProcessLoginFailure();
        }
    }
}