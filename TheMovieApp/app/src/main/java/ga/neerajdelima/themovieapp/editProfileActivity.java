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
    String firstName;
    String userName;
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
        userNameText.setText(currentUser.getUsername());
        firstNameText.setText(currentUser.getFirstName());

        firstName = firstNameText.getText().toString();
        userName = userNameText.getText().toString();

        if (firstName == null) {
            firstNameText.setText("Enter First Name Here");
        }
    }
    public void saveChanges(View view) {

        userNameText.setText(userNameText.getText());
        firstNameText.setText(firstNameText.getText());

        firstName = firstNameText.getText().toString();
        userName = userNameText.getText().toString();

        if (firstName != null & userName != null) {
            currentUser.setFirstName(firstName);
            currentUser.setUsername(userName);
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

    }
}
