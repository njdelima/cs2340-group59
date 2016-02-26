package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchTask;
import ga.neerajdelima.themovieapp.model.network.NetworkCheckTask;

/**
 * Class that handles LoginActivity.
 * @author
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity {

    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userModel = new UserModel();
    }
    /**
     * Method for login.
     * Check the user's username and password,
     * and navigate the user to the home screen if username and password match,
     * show the error message if not matched.
     * @param view the current view of the login screen
     */
    public void checkLogin(View view) {
        new LoginNetworkCheckTask().execute();
    }
    /**
     * Method to navigate the user to the register screen.
     * @param view the register button
     */
    public void registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /*
     * This task just checks the network connectivity.
     * See ga.neerajdelima.themovieapp.model.network.NetworkCheckTask
     * for what it does in the background.
     *
     * If the connection succeeds, it calls the ProcessLoginTask
     */
    private class LoginNetworkCheckTask extends NetworkCheckTask {
        public LoginNetworkCheckTask() {
            super("http://128.61.104.207:2340/api/users/fetch.php");
        }

        @Override
        protected void onPostExecute(Object response) {
            boolean success = (boolean) response;
            if (success) {
                new ProcessLoginTask().execute();
            } else {
                Toast.makeText(LoginActivity.this, "Error in Network Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     * This task processes the login attempt. Checks the password
     * provided against the one in the database for the user
     * and if they match, logs him in and goes to HomeActivity.
     */
    private class ProcessLoginTask extends FetchTask {

        String username;
        String password;

        public ProcessLoginTask() {
            super("http://128.61.104.207:2340/api/users/fetch.php");
        }

        /*
         * Get the username and password from the View
         */
        @Override
        protected void onPreExecute() {
            EditText usernameText = (EditText) findViewById(R.id.username_text);
            EditText passwordText = (EditText) findViewById(R.id.password_text);

            username = usernameText.getText().toString();
            password = userModel.md5(passwordText.getText().toString());
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
            if (success) { // Password checks out , go to HomeActivity
                Log.d("About to set logged in user as", username);
                userModel.setLoggedInUser(username);
                Log.d("Finished setting logged in user", username);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(LoginActivity.this, "Incorrect Username/Password Combination", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

