package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import ga.neerajdelima.themovieapp.model.UserModel;
/**
 * Class that handles RegisterActivity.
 * @author
 * @version 1.0
 */
public class RegisterActivity extends AppCompatActivity {

    Intent intent;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intent = this.getIntent();
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
        EditText usernameText = (EditText) findViewById(R.id.register_username_text);
        EditText passwordText = (EditText) findViewById(R.id.register_password_text);
        EditText confirmPasswordText = (EditText) findViewById(R.id.register_password_confirm);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();

        if (password.equals(confirmPassword)) {
            userModel.addUser(username, password);
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            TextView errorMessage = new TextView(this);
            errorMessage.setText(R.string.register_fail);
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.register_layout);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.BELOW, R.id.register_cancel_button);

            relativeLayout.addView(errorMessage, params);
        }

    }
    /**
     * Method to navigate back the user to the login screen.
     * @param view the cancel button
     */
    public void cancelRegistration(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
