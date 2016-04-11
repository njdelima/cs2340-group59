package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;


import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.UtilitiesModel;
import ga.neerajdelima.themovieapp.model.network.NetworkCheckResponse;
import ga.neerajdelima.themovieapp.model.network.ProcessLoginResponse;

/**
 * Class that handles LoginActivity.
 * @author Neeraj Delima
 * @version 1.0
 */
public class LoginActivity extends AppCompatActivity implements NetworkCheckResponse, ProcessLoginResponse {

    private UserModel userModel;
    private UtilitiesModel utilitiesModel;

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
        final EditText usernameText = (EditText) findViewById(R.id.username_text);
        final EditText passwordText = (EditText) findViewById(R.id.password_text);

        final String username = usernameText.getText().toString();
        final String password = userModel.md5(passwordText.getText().toString());
        userModel.processLogin(this, username, password);
    }

    @Override
    public void onNetworkCheckFailure() {
        Toast.makeText(LoginActivity.this, "Error in Network Connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessLoginAsAdmin(String username) {
        //Log.d("About to set logged in admin as: ", username);
        userModel.setLoggedInUser(username);
        //Log.d("Finished setting logged in admin", username);
        final Intent intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProcessLoginSuccess(String username) {
        //Log.d("About to set logged in user as", username);
        userModel.setLoggedInUser(username);
        //Log.d("Finished setting logged in user", username);
        final Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onProcessLoginFailure() {
        Toast.makeText(LoginActivity.this, "Incorrect Username/Password Combination", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessLoginBan() {
        Toast.makeText(LoginActivity.this, "You have been BANNED by an Admin!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProcessLoginLocked() {
        Toast.makeText(LoginActivity.this, "You have been Locked by an Admin!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to navigate the user to the register screen.
     * @param view the register button
     */
    public void registerClick(View view) {
        //Intent intent = new Intent(this, HomeActivity.class);
        final Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}

