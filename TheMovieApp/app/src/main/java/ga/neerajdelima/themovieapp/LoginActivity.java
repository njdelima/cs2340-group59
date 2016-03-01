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
import ga.neerajdelima.themovieapp.model.UtilitiesModel;
import ga.neerajdelima.themovieapp.model.network.FetchTask;
import ga.neerajdelima.themovieapp.model.network.NetworkCheckResponse;
import ga.neerajdelima.themovieapp.model.network.NetworkCheckTask;
import ga.neerajdelima.themovieapp.model.network.ProcessLoginResponse;

/**
 * Class that handles LoginActivity.
 * @author
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity implements NetworkCheckResponse, ProcessLoginResponse {

    UserModel userModel;
    UtilitiesModel utilitiesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userModel = new UserModel();
        utilitiesModel = new UtilitiesModel();
    }
    /**
     * Method for login.
     * Check the user's username and password,
     * and navigate the user to the home screen if username and password match,
     * show the error message if not matched.
     * @param view the current view of the login screen
     */
    public void checkLogin(View view) {
        utilitiesModel.checkNetworkConnection(this);
    }

    @Override
    public void onNetworkCheckSuccess() {
        EditText usernameText = (EditText) findViewById(R.id.username_text);
        EditText passwordText = (EditText) findViewById(R.id.password_text);

        String username = usernameText.getText().toString();
        String password = userModel.md5(passwordText.getText().toString());
        userModel.processLogin(this, username, password);
    }

    @Override
    public void onNetworkCheckFailure() {
        Toast.makeText(LoginActivity.this, "Error in Network Connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessLoginSuccess(String username) {
        Log.d("About to set logged in user as", username);
        userModel.setLoggedInUser(username);
        Log.d("Finished setting logged in user", username);
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProcessLoginFailure() {
        Toast.makeText(LoginActivity.this, "Incorrect Username/Password Combination", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to navigate the user to the register screen.
     * @param view the register button
     */
    public void registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}

