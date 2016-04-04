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
public class ViewProfileActivity extends AppCompatActivity {
    private UserModel userModel;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        userModel = new UserModel();
        User currentUser = userModel.getLoggedInUser();
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
        TextView userName = (TextView) findViewById(R.id.view_profile_userName);
        userName.setText(currentUser.getUsername());
        TextView firstName = (TextView) findViewById(R.id.view_profile_firstName);
        firstName.setText(currentUser.getFirstName());
        TextView lastName = (TextView) findViewById(R.id.view_profile_lastName);
        lastName.setText(currentUser.getLastName());
        TextView passWord = (TextView) findViewById(R.id.view_profile_password);
        passWord.setText(currentUser.getPassword());
        TextView major = (TextView) findViewById(R.id.view_profile_major);
        major.setText(currentUser.getMajor());
    }

    /**
     * Method to handle the different options presented in the navigation bar
     * @param view the current view of the navigation bar
     */
    private void handleNavClick(View view) {
        String label = ((TextView) view).getText().toString();
        if ("Logout".equals(label)) {
            logout();
        }
        if ("Profile".equals(label)) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        if ("Search".equals(label)){
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        if ("Home".equals(label)) {
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
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, optsArray);
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
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}
