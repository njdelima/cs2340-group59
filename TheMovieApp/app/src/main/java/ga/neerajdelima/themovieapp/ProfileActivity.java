package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ga.neerajdelima.themovieapp.model.UserModel;

/**
 * Profile Activity
 * @author Komal Hirani
 * @version 1.0
 */

public class ProfileActivity extends AppCompatActivity {
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        userModel = new UserModel();
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
     * Method to view the user's profile page when the view profile button on the main profile screen is clicked
     * @param view the current view of the main profile screen
     */
    public void viewProfile(View view) {
        if (userModel.getLoggedInUser().getFirstName() != null) {
            Intent intent = new Intent(this, ViewProfileActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(ProfileActivity.this, "Profile has not been created", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method to go to the edit profile page when the edit profile button on the main profile screen is clicked
     * @param view the current view of the main profile screen
     */
    public void editProfile(View view) {
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}
