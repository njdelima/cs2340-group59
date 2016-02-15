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

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        User toAdd = new User(username, password);
        valid_credentials.add(toAdd);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.putParcelableArrayListExtra("CREDENTIALS", valid_credentials);
        startActivity(intent);
    }
    public void cancelRegistration(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putParcelableArrayListExtra("CREDENTIALS", valid_credentials);
        startActivity(intent);
    }
}
