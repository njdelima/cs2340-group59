package com.nullpointexecutioners.buzzfilms.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.channguyen.rsv.RangeSliderView;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.nullpointexecutioners.buzzfilms.R;
import com.nullpointexecutioners.buzzfilms.Users;
import com.nullpointexecutioners.buzzfilms.adapters.UsersAdapter;
import com.nullpointexecutioners.buzzfilms.helpers.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {

    @Bind(R.id.admin_toolbar) Toolbar toolbar;
    @Bind(R.id.refresh_container) SwipeRefreshLayout refreshContainer;
    @Bind(R.id.users_list) ListView mUsersList;
    @BindDrawable(R.drawable.rare_pepe_avatar) Drawable mProfileDrawerIcon;
    @BindString(R.string.admin) String admin;
    @BindString(R.string.admin_controls) String adminControls;
    @BindString(R.string.dashboard) String dashboard;
    @BindString(R.string.list_of_users) String listOfUsers;
    @BindString(R.string.profile) String profile;
    @BindString(R.string.recent_releases) String recentReleases;
    @BindString(R.string.settings) String settings;
    @BindString(R.string.status_active) String active;
    @BindString(R.string.status_banned) String banned;
    @BindString(R.string.status_locked) String locked;

    Drawer mNavDrawer;
    private final Firebase mUsersRef = new Firebase("https://buzz-films.firebaseio.com/users");
    private List<Users> users = new ArrayList<>();
    private SessionManager mSession;
    private static final int DELAY = 1000; //in ms
    private UsersAdapter mUsersAdapter;

    private static final int PROFILE = 1;
    private static final int DASHBOARD = 2;
    private static final int RECENT_RELEASES = 3;
    private static final int ADMIN = 4;
    private static final int SETTINGS = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        ButterKnife.bind(this);

        this.mSession = SessionManager.getInstance(getApplicationContext());

        initToolbar();
        createNavDrawer();
        setupUsersList();

        //This is necessary to allow us to scroll up since we have a SwipeRefreshlayout
        mUsersList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                final int topRowVerticalPosition =
                        (mUsersList == null || mUsersList.getChildCount() == 0) ?
                                0 : mUsersList.getChildAt(0).getTop();
                refreshContainer.setEnabled(firstVisibleItem == 0 && topRowVerticalPosition >= 0);
            }
        });

        refreshContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContainer.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setupUsersList();
                    }
                }, DELAY);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * Populates list of users
     */
    private void setupUsersList() {
        if (users != null && mUsersAdapter != null) {
            users.clear();
            mUsersAdapter.clear();
        }

        mUsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //iterate through all of the reviews for the movie
                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    if (("is_admin").equals(child.getKey())) {
                        continue;
                    }
                    final String username = child.child("username").getValue(String.class);
                    final String name = child.child("name").getValue(String.class);
                    final String email = child.child("email").getValue(String.class);
                    final String major = child.child("major").getValue(String.class);
                    final String status = child.child("status").getValue() != null
                            ? child.child("status").getValue(String.class) : "ACTIVE";

                    users.add(new Users(username, name, email, major, status));
                }
                if (!users.isEmpty()) {
                    mUsersAdapter = new UsersAdapter(AdminActivity.this,
                            R.layout.user_list_item, new ArrayList<Users>());
                    mUsersList.setAdapter(mUsersAdapter);
                    mUsersAdapter.addAll(users);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        refreshContainer.setRefreshing(false);

        //Handle passing the selected user in the list
        mUsersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long arg3) {
                final String username = users.get(position).getUsername();
                final String name = users.get(position).getName();
                final String email = users.get(position).getEmail();
                final String major = users.get(position).getMajor();
                String status = users.get(position).getStatus();
                //Want the value in Firebase to remain one of "Active", "Locked", or "Banned";
                //but if we decide to display it differently on the UI-side, then we need to translate those Strings
                switch (status) {
                    case ("ACTIVE"):
                        status = active;
                        break;
                    case ("LOCKED"):
                        status = locked;
                        break;
                    case ("BANNED"):
                        status = banned;
                        break;
                }
                userView(username, name, email, major, status);
            }
        });
    }

    /**
     * Method is to view the current user information
     * @param username The username of a user
     * @param name the name of a user
     * @param email the email of a user
     * @param major the major of a user
     * @param status banned or active
     */
    private void userView(final String username, String name, String email, String major, String status) {
        final MaterialDialog userDialog = new MaterialDialog.Builder(this)
                .title(username)
                .customView(R.layout.user_view_dialog, true)
                .positiveText(R.string.okay)
                .negativeText(R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog userDialog, @NonNull DialogAction which) {
                        final TextView statusText = ButterKnife.findById(userDialog, R.id.current_status);
                        String selectedStatus = statusText.getText().toString();
                        if (selectedStatus.equals(active)) {
                            selectedStatus = "ACTIVE";
                        } else if (selectedStatus.equals(locked)) {
                            selectedStatus = "LOCKED";
                        } else if (selectedStatus.equals(banned)) {
                            selectedStatus = "BANNED";
                        }

                        Firebase userRef = mUsersRef.child(username);
                        HashMap<String, Object> updateValues = new HashMap<>();
                        updateValues.put("status", selectedStatus);

                        userRef.updateChildren(updateValues); //Update Firebase with new user status
                        refreshContainer.setRefreshing(true);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setupUsersList();
                            }
                        }, DELAY);
                    }
                })
                .build();

        final TextView nameText = ButterKnife.findById(userDialog, R.id.user_view_name);
        nameText.setText(name);
        final TextView emailText = ButterKnife.findById(userDialog, R.id.user_view_email);
        emailText.setText(email);
        final TextView majorText = ButterKnife.findById(userDialog, R.id.user_view_major);
        majorText.setText(major);
        final TextView statusText = ButterKnife.findById(userDialog, R.id.current_status);
        statusText.setText(status);

        final RangeSliderView currentStatus = ButterKnife.findById(userDialog, R.id.current_status_bar);
        if (status.equals(active)) {
            currentStatus.setInitialIndex(0);
        } else if (status.equals(locked)) {
            currentStatus.setInitialIndex(1);
        } else if (status.equals(banned)) {
            currentStatus.setInitialIndex(2);
        }
        currentStatus.setOnSlideListener(new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                switch (index) {
                    case 0:
                        statusText.setText(active);
                        break;
                    case 1:
                        statusText.setText(locked);
                        break;
                    case 2:
                        statusText.setText(banned);
                        break;
                }
            }
        });
        userDialog.show();
    }

    /**
     * Helper method to create the nav drawer for the MainActivity
     */
    private void createNavDrawer() {
        //Create the AccountHeader for the nav drawer
        final AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.accent)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName(mSession.getLoggedInName())
                                .withEmail(mSession.getLoggedInEmail())
                                .withIcon(mProfileDrawerIcon))
                .withSelectionListEnabledForSingleProfile(false)
                .build();
        //Create the nav drawer
        mNavDrawer = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(profile).withIcon(GoogleMaterial.Icon.gmd_person).withIdentifier(PROFILE).withSelectable(false),
                        new PrimaryDrawerItem().withName(dashboard).withIcon(GoogleMaterial.Icon.gmd_dashboard).withIdentifier(DASHBOARD).withSelectable(false),
                        new PrimaryDrawerItem().withName(recentReleases).withIcon(GoogleMaterial.Icon.gmd_local_movies).withIdentifier(RECENT_RELEASES).withSelectable(false),
                        new PrimaryDrawerItem().withName(admin).withIcon(GoogleMaterial.Icon.gmd_face).withIdentifier(ADMIN),
                        new SecondaryDrawerItem().withName(settings).withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(SETTINGS).withSelectable(false))
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem != null && drawerItem instanceof Nameable) {
                            Intent intent;

                            switch(drawerItem.getIdentifier()) {
                                case PROFILE:
                                    mNavDrawer.closeDrawer();
                                    intent = new Intent(AdminActivity.this, ProfileActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    return true;
                                case DASHBOARD:
                                    mNavDrawer.closeDrawer();
                                    intent = new Intent(AdminActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    return true;
                                case RECENT_RELEASES:
                                    mNavDrawer.closeDrawer();
                                    intent = new Intent(AdminActivity.this, RecentReleasesActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    return true;
                                case ADMIN:
                                    return false;
                                case SETTINGS:
                                    //TODO, handle Settings
                                    return false;
                            }
                        }
                        return false;
                    }
                }).build();
        mNavDrawer.setSelection(ADMIN);
    }

    @Override
    public void onBackPressed() {
        if (mNavDrawer != null && mNavDrawer.isDrawerOpen()) {
            mNavDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Helper method that inits all of the Toolbar stuff.
     * Specifically:
     * sets Toolbar title, enables the visibility of the overflow menu
     */
    private void initToolbar() {
        toolbar.setTitle(adminControls);
        toolbar.setSubtitle(listOfUsers);
        setSupportActionBar(toolbar);
    }
}
