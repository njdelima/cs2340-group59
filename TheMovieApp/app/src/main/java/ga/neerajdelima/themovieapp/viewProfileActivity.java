package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.User;

/**
 * This class represents all of the activity that is shown on the view profile screen
 * @author Komal Hirani
 * @version 1.0
 */
public class viewProfileActivity extends AppCompatActivity {
    Intent intent;
    UserModel userModel;
    User currentUser;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    TextView userName;
    TextView firstName;
    TextView lastName;
    TextView passWord;
    TextView major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        intent = this.getIntent();
        userModel = new UserModel();
        currentUser = userModel.getLoggedInUser();
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
        userName = (TextView) findViewById(R.id.view_profile_userName);
        userName.setText(currentUser.getUsername());
        firstName = (TextView) findViewById(R.id.view_profile_firstName);
        firstName.setText(currentUser.getFirstName());
        lastName = (TextView) findViewById(R.id.view_profile_lastName);
        lastName.setText(currentUser.getLastName());
        passWord = (TextView) findViewById(R.id.view_profile_password);
        passWord.setText(currentUser.getPassword());
        major = (TextView) findViewById(R.id.view_profile_major);
        major.setText(currentUser.getMajor());
    }

    /**
     * Method to handle the different options presented in the navigation bar
     * @param view the current view of the navigation bar
     */
    private void handleNavClick(View view) {
        String label = ((TextView) view).getText().toString();
        if (label.equals("Logout")) {
            logout();
        }
        if (label.equals("Profile")) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        if (label.equals("Search")){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        if (label.equals("Home")) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
    /**
     *
     * Method to add the different options to the drawer in the navigation bar
     * @param optsArray the array that lists all of the options presented in the navigation bar
     */
    private void addDrawerItems(String[] optsArray) {
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, optsArray);
        mDrawerList.setAdapter(mAdapter);
    }

    /**
     * Method that enables the current logged in user to log out from their account
     */
    private void logout() {
        userModel.setLoggedInUser(null);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * Method to go to the edit profile page when the edit profile button on the view profile screen is clicked
     * @param view the current view of the view profile screen
     */
    public void editProfile(View view) {
        Intent intent = new Intent(this, editProfileActivity.class);
        startActivity(intent);
    }
}
