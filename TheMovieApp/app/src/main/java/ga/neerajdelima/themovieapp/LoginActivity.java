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

import ga.neerajdelima.themovieapp.model.UserModel;
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
        userModel.logAllUsers();
    }
    /**
     * Method for login.
     * Check the user's username and password,
     * and navigate the user to the home screen if username and password match,
     * show the error message if not matched.
     * @param view the current view of the login screen
     */
    public void checkLogin(View view) {
        EditText usernameText = (EditText) findViewById(R.id.username_text);
        EditText passwordText = (EditText) findViewById(R.id.password_text);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        //if (username.equals("user") && password.equals("pass")) {
        if (userModel.checkLogin(username, password)) {
            userModel.logUserIn(username);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            TextView errorMessage = new TextView(this);
            errorMessage.setText(R.string.login_fail);

            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.main_layout);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.BELOW, R.id.login_button);

            relativeLayout.addView(errorMessage, params);
        }
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
