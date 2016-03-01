package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class editProfileActivity extends AppCompatActivity {

    Intent intent;
    UserModel userModel;
    User currentUser;
    EditText userNameText;
    EditText firstNameText;
    EditText lastNameText;
    EditText passwordText;
<<<<<<< HEAD
    //EditText majorText;
=======
   // EditText majorText;
>>>>>>> min-ho
    String firstName;
    String userName;
    String lastName;
    String password;
    String major;
    String oldPassword;
    Spinner spinner;
<<<<<<< HEAD
    //String selected;
    String[] majors;
    int sp_position;

=======
    String selected;
    String[] majors;
    int sp_position;
>>>>>>> min-ho
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        intent = this.getIntent();
        userModel = new UserModel();
        currentUser = userModel.getLoggedInUser();
        spinner = (Spinner) findViewById(R.id.edit_major_spinner);
<<<<<<< HEAD
        //spinner.setAdapter(new ArrayAdapter<Major>(this, android.R.layout.simple_spinner_item, Major.values()));
        //userNameText.setHint(currentUser.getUsername());
=======
>>>>>>> min-ho
        major = currentUser.getMajor();
        majors = getResources().getStringArray(R.array.majors_array);
        ArrayAdapter<String> ad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, majors);
        sp_position = ad.getPosition(major);
        spinner.setAdapter(ad);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setSelection(sp_position);

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

        firstName = firstNameText.getText().toString();
        userName = userNameText.getText().toString();
        lastName = lastNameText.getText().toString();
        password = passwordText.getText().toString();
        major = String.valueOf(spinner.getSelectedItem());

        password = password.equals(oldPassword) ? password : userModel.md5(password);


        if (firstName.equals("") | userName.equals("") | lastName.equals("")
                | password.equals("") | major.equals("")) {
            Toast.makeText(editProfileActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            userModel.updateProfile(currentUser.getUsername(), userName,password, firstName, lastName, major);
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
    }
}
