package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.User;

/**
 * Profile Activity
 * @author Komal Hirani
 * @version 1.0
 */

public class ProfileActivity extends AppCompatActivity {
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    UserModel userModel = new UserModel();

    EditText usernameText, firstNameText, lastNameText, majorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mDrawerList = (ListView) findViewById(R.id.navList);
        String[] optsArray = getResources().getStringArray(R.array.navigation_array);
        addDrawerItems(optsArray);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleNavClick(view);
                // Toast.makeText(HomeActivity.this, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        usernameText = (EditText) findViewById(R.id.profile_username_text);
        firstNameText = (EditText) findViewById(R.id.profile_firstname_text);
        lastNameText = (EditText) findViewById(R.id.profile_lastname_text);
        majorText = (EditText) findViewById(R.id.profile_major_text);

        populateFields();


    }
    private void populateFields() {
        User currentUser = userModel.getLoggedInUser();

        usernameText.setText(currentUser.getUsername());

        firstNameText.setText(currentUser.getFirstName());

        lastNameText.setText(currentUser.getLastName());

        majorText.setText(currentUser.getMajor());
    }

    public void updateProfile(View view) {
        String firstName = firstNameText.getText().toString();
        String lastName = lastNameText.getText().toString();
        String major = majorText.getText().toString();

        userModel.updateProfile(firstName, lastName, major);
    }
    private void handleNavClick(View view) {
        String label = ((TextView) view).getText().toString();
        if (label.equals("Logout")) {
            logout();
        }
        if (label.equals("Profile")) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
    }

    private void addDrawerItems(String[] optsArray) {
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, optsArray);
        mDrawerList.setAdapter(mAdapter);
    }

    private void logout() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
