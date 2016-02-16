package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ga.neerajdelima.themovieapp.model.User;
import ga.neerajdelima.themovieapp.model.UserModel;

public class editProfileActivity extends AppCompatActivity {

    Intent intent;
    UserModel userModel;
    User currentUser;
    EditText userNameText;
    EditText firstNameText;
    EditText lastNameText;
    EditText passwordText;
    EditText majorText;
    String firstName;
    String userName;
    String lastName;
    String password;
    String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        intent = this.getIntent();
        userModel = new UserModel();
        currentUser = userModel.getLoggedInUser();
        //userNameText.setHint(currentUser.getUsername());
        userNameText = (EditText) findViewById(R.id.edit_userName);
        firstNameText = (EditText) findViewById(R.id.edit_firstName);
        lastNameText = (EditText) findViewById(R.id.edit_lastName);
        passwordText = (EditText) findViewById(R.id.edit_password);
        majorText = (EditText) findViewById(R.id.edit_major);

        userNameText.setText(currentUser.getUsername());
        firstNameText.setText(currentUser.getFirstName());
        lastNameText.setText(currentUser.getLastName());
        passwordText.setText(currentUser.getPassword());
        majorText.setText(currentUser.getMajor());

    }
    public void saveChanges(View view) {

        userNameText.setText(userNameText.getText());
        firstNameText.setText(firstNameText.getText());
        lastNameText.setText(lastNameText.getText());
        passwordText.setText(passwordText.getText());
        majorText.setText(majorText.getText());

        firstName = firstNameText.getText().toString();
        userName = userNameText.getText().toString();
        lastName = lastNameText.getText().toString();
        password = passwordText.getText().toString();
        major = majorText.getText().toString();

        if (firstName != null & userName != null & lastName != null & password != null & major != null) {
            currentUser.setFirstName(firstName);
            currentUser.setUsername(userName);
            currentUser.setLastName(lastName);
            currentUser.setPassword(password);
            currentUser.setMajor(major);
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }


    }
}
