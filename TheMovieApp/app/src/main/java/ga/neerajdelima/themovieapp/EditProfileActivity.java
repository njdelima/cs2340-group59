package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import ga.neerajdelima.themovieapp.model.User;
import ga.neerajdelima.themovieapp.model.UserModel;

/**
 * Class that handles all of the activity on the edit Profile Screen
 * @author Komal Hirani
 * @version 1.0
 */

public class EditProfileActivity extends AppCompatActivity {
    private UserModel userModel;
    private User currentUser;
    private EditText userNameText;
    private EditText firstNameText;
    private EditText lastNameText;
    private EditText passwordText;
    private String major;
    private String oldPassword;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userModel = new UserModel();
        currentUser = userModel.getLoggedInUser();
        spinner = (Spinner) findViewById(R.id.edit_major_spinner);
        major = currentUser.getMajor();
        String[] majors = getResources().getStringArray(R.array.majors_array);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, majors);
        int spPosition = ad.getPosition(major);
        spinner.setAdapter(ad);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(spPosition);

//      ArrayAdapter arr = (ArrayAdapter) spinner.getAdapter();
//        int spinnerPos = arr.getPosition(major);
        //int pos = spinner.getSelectedItemPosition();
        //spinner.setSelection(pos);
        userNameText = (EditText) findViewById(R.id.edit_userName);
        firstNameText = (EditText) findViewById(R.id.edit_firstName);
        lastNameText = (EditText) findViewById(R.id.edit_lastName);
        passwordText = (EditText) findViewById(R.id.edit_password);

        userNameText.setText(currentUser.getUsername());
        firstNameText.setText(currentUser.getFirstName());
        lastNameText.setText(currentUser.getLastName());
        passwordText.setText(currentUser.getPassword());

        oldPassword = currentUser.getPassword();
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

        String firstName = firstNameText.getText().toString();
        String userName = userNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String password = passwordText.getText().toString();
        major = String.valueOf(spinner.getSelectedItem());

        password = password.equals(oldPassword) ? password : userModel.md5(password);


        if ("".equals(firstName) || "".equals(userName) || "".equals(lastName)
                || "".equals(password) || "".equals(major)) {
            Toast.makeText(EditProfileActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            userModel.updateProfile(currentUser.getUsername(), userName,password, firstName, lastName, major);
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
    }
}
