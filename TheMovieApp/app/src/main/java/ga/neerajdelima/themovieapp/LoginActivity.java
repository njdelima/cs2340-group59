package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    ArrayList<User> valid_credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        valid_credentials = this.getIntent().getParcelableArrayListExtra("CREDENTIALS");
        if (valid_credentials == null) {
            valid_credentials = new ArrayList<User>();
            valid_credentials.add(new User("user", "pass"));
        }
    }

    public void checkLogin(View view) {
        EditText usernameText = (EditText) findViewById(R.id.username_text);
        EditText passwordText = (EditText) findViewById(R.id.password_text);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        User toCheck = new User(username, password);

        //if (username.equals("user") && password.equals("pass")) {
        if (valid_credentials.contains(toCheck)) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putParcelableArrayListExtra("CREDENTIALS", valid_credentials);
            intent.putExtra("USERNAME", username);
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
    public void registerClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putParcelableArrayListExtra("CREDENTIALS", valid_credentials);
        startActivity(intent);
    }
}
