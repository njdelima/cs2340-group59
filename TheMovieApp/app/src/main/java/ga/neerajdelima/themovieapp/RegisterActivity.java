package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    Intent intent;
    ArrayList<User> valid_credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        intent = this.getIntent();
        valid_credentials = intent.getParcelableArrayListExtra("CREDENTIALS");
    }

    public void checkRegister(View view) {
        EditText usernameText = (EditText) findViewById(R.id.register_username_text);
        EditText passwordText = (EditText) findViewById(R.id.register_password_text);
        EditText confirmPasswordText = (EditText) findViewById(R.id.register_password_confirm);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();
//        boolean checker = true;
        if (password.equals(confirmPassword)) {
            User toAdd = new User(username, password);
            valid_credentials.add(toAdd);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putParcelableArrayListExtra("CREDENTIALS", valid_credentials);
            startActivity(intent);
        } else {
            TextView errorMessage = new TextView(this);
            errorMessage.setText(R.string.register_fail);
//            passwordText = (EditText) findViewById(R.id.register_password_text);
//            confirmPasswordText = (EditText) findViewById(R.id.register_password_confirm);
//            password = passwordText.getText().toString();
//            confirmPassword = confirmPasswordText.getText().toString();
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.register_layout);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.BELOW, R.id.register_cancel_button);

            relativeLayout.addView(errorMessage, params);
        }
    }
    public void cancelRegistration(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putParcelableArrayListExtra("CREDENTIALS", valid_credentials);
        startActivity(intent);
    }
}
