package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;


import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchTask;
import ga.neerajdelima.themovieapp.model.network.NetworkCheckTask;

/**
 * Class that handles RegisterActivity.
 * @author
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity {
    private Intent intent;
    private UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userModel = new UserModel();
    }
    /**
     * Method to create the user's new account.
     * The user puts new Username, Password, and Confirm Password.
     * Create new account if Password matches the confirm password.
     * and navigate the user back to the login screen.
     * Show the user the error message if Password does not match the confirm password.
     * @param view the current view of the register screen
     */
    public void checkRegister(View view) {
        new RegisterNetworkCheckTask().execute();
    }
    /**
     * Method to navigate back the user to the login screen.
     * @param view the cancel button
     */
    public void cancelRegistration(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /*
     * Same as login, checks the network connection before attempting registration
     */
    private class RegisterNetworkCheckTask extends NetworkCheckTask {

        public RegisterNetworkCheckTask() {
            super("http://128.61.104.207:2340/api/users/add.php");
        }

        @Override
        protected void onPostExecute(Object response) {
            boolean success = (boolean) response;
            if (success) {
                new ProcessRegisterTask().execute();
            } else {
                Toast.makeText(RegisterActivity.this, "Error in Network Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
     * Process the registration. Pull the data from the View and add it to
     * the database
     */
    private class ProcessRegisterTask extends FetchTask {

        private String username;
        private String password;
        private String confirmPassword;

        public ProcessRegisterTask() {
            super("http://128.61.104.207:2340/api/users/add.php");
        }

        @Override
        protected void onPreExecute() {
            EditText usernameText = (EditText) findViewById(R.id.register_username_text);
            EditText passwordText = (EditText) findViewById(R.id.register_password_text);
            EditText confirmPasswordText = (EditText) findViewById(R.id.register_password_confirm);

            username = usernameText.getText().toString();
            password = userModel.md5(passwordText.getText().toString());
            confirmPassword = userModel.md5(confirmPasswordText.getText().toString());
        }

        @Override
        protected Boolean doInBackground(Object... args) {
            try {
                if (!password.equals(confirmPassword)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    });
                    return false;
                }
                connection.setConnectTimeout(0);
                JSONObject data = new JSONObject();
                data.put("username", username);
                data.put("password", password);
                sendPostData(data);
                Log.d("Checkpoint", "made it past x sendpostdata");
                Log.d("Response mess", getResponseMessage());
                //if (!getInputString().equals("Success!")) {
                 //   runOnUiThread(new Runnable() {
                  //      @Override
                   //     public void run() {
                    //        Toast.makeText(RegisterActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                     //   }
                 //   });
                  //  return false;
                //}
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
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        }
    }
}
