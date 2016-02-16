package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ga.neerajdelima.themovieapp.model.User;
import ga.neerajdelima.themovieapp.model.UserModel;

/**
 * Class that handles all of the activity on the edit Profile Screen
 * @author Komal Hirani
 * @version 1.0
 */

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

    /**
     * Method that saves all of the changes that the user makes to his/her profile
     * @param view the current view of the edit profile screen
     */
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

        if (firstName.equals("") | userName.equals("") | lastName.equals("")
                | password.equals("") | major.equals("")) {
            TextView errorMessage = new TextView(this);
            errorMessage.setText("Please fill in all slots");
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.editProfile_layout);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            params.addRule(RelativeLayout.BELOW, R.id.saveChanges);
            relativeLayout.addView(errorMessage, params);
        } else {
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
