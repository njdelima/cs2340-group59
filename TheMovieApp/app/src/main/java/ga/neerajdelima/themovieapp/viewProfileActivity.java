package ga.neerajdelima.themovieapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
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

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        userModel = new UserModel();
        final User currentUser = userModel.getLoggedInUser();

        getSupportActionBar().setTitle(Html.fromHtml("<font color=#ecb540>" + getSupportActionBar().getTitle() + "</font>"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        mDrawerList = (ListView) findViewById(R.id.navList);
        final String[] optsArray = getResources().getStringArray(R.array.navigation_array);
        addDrawerItems(optsArray);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleNavClick(view);
                // Toast.makeText(HomeActivity.this, ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        setupDrawer();

        final TextView userName = (TextView) findViewById(R.id.view_profile_userName);
        userName.setText(currentUser.getUsername());
        final TextView firstName = (TextView) findViewById(R.id.view_profile_firstName);
        firstName.setText(currentUser.getFirstName());
        final TextView lastName = (TextView) findViewById(R.id.view_profile_lastName);
        lastName.setText(currentUser.getLastName());
        final TextView major = (TextView) findViewById(R.id.view_profile_major);
        major.setText(currentUser.getMajor());
    }
    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    /**
     * Method to handle the different options presented in the navigation bar
     * @param view the current view of the navigation bar
     */
    private void handleNavClick(View view) {
        final String label = ((TextView) view).getText().toString();
        if ("Logout".equals(label)) {
            logout();
        }
        if ("Profile".equals(label)) {
            final Intent intent = new Intent(this, ViewProfileActivity.class);
            startActivity(intent);
        }
        if ("Search".equals(label)){
            final Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
        if ("Home".equals(label)) {
            final Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
    }
    /**
     *
     * Method to add the different options to the drawer in the navigation bar
     * @param optsArray the array that lists all of the options presented in the navigation bar
     */
    private void addDrawerItems(String[] optsArray) {
        final ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, optsArray);
        mDrawerList.setAdapter(mAdapter);
    }

    /**
     * Method that enables the current logged in user to log out from their account
     */
    private void logout() {
        userModel.setLoggedInUser(null);
        final Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    /**
     * Method to go to the edit profile page when the edit profile button on the view profile screen is clicked
     * @param view the current view of the view profile screen
     */
    public void editProfile(View view) {
        final Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }
}
