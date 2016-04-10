package com.nullpointexecutioners.buzzfilms.activities;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsSpinner;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.nullpointexecutioners.buzzfilms.Major;
import com.nullpointexecutioners.buzzfilms.R;
import com.nullpointexecutioners.buzzfilms.helpers.SessionManager;
import com.nullpointexecutioners.buzzfilms.helpers.StringHelper;
import com.nullpointexecutioners.buzzfilms.helpers.ViewHelper;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * User's Profile for their account
 */
public class ProfileActivity extends AppCompatActivity {

    @Bind(android.R.id.content) View thisActivity;
    @Bind(R.id.currentEmail) TextView profileEmail;
    @Bind(R.id.currentInterests) TextView profileInterests;
    @Bind(R.id.currentMajor) TextView profileMajor;
    @Bind(R.id.currentName) TextView profileName;
    @Bind(R.id.profile_fab) FloatingActionButton floatingActionButton;
    @Bind(R.id.profile_toolbar) Toolbar toolbar;
    @BindInt(R.color.accent) int accentColor;
    @BindInt(R.color.primary_text_light) int primaryTextLightColor;
    @BindString(R.string.cancel) String cancel;
    @BindString(R.string.edit_password_dialog_title) String editPasswordDialogTitle;
    @BindString(R.string.edit_profile_dialog_title) String editProfileDialogTitle;
    @BindString(R.string.major_not_specified) String majorNotSpecified;
    @BindString(R.string.network_not_available) String invalidEmail;
    @BindString(R.string.new_password_mismatch) String passwordMismatch;
    @BindString(R.string.password_change_success) String passwordChangeSuccess;
    @BindString(R.string.password_incorrect) String passwordIncorrect;
    @BindString(R.string.save) String save;

    private SessionManager mSession;

    Major mMajor;
    String mEmail;
    String mInterests;
    String mName;
    String mUsername;

    private final Firebase mRef = new Firebase("https://buzz-films.firebaseio.com/users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        final int SIZE_DP = 24;
        final int PADDING_DP = 2;
        Drawable editIcon = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_edit)
                .color(Color.BLACK)
                .sizeDp(SIZE_DP)
                .paddingDp(PADDING_DP);
        floatingActionButton.setImageDrawable(editIcon);

        this.mSession = SessionManager.getInstance(getApplicationContext());

        setupProfile();
        initToolbar();
    }

    /**
     * Helper method to setup and display the user's information in the Profile view
     */
    private void setupProfile() {
        /*Get the user's info*/
        mUsername = mSession.getLoggedInUsername();
        mName = mSession.getLoggedInName();
        mEmail = mSession.getLoggedInEmail();
        String major = mSession.getLoggedInMajor();

        //Set the current user's attributes
        profileName.setText(mName);
        profileEmail.setText(mEmail);

        //Get current user's major and set the field accordingly
        if (!("NONE").equals(major)) {
            mMajor = Major.fromString(major);
            profileMajor.setText(mMajor.toString());
        } else {
            profileMajor.setText(majorNotSpecified);
        }

        /*Get Interests from Firebase*/
        mRef.child(mUsername).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String interests = dataSnapshot.child("interests").getValue(String.class);

                if (interests != null) {
                    mInterests = interests;
                    profileInterests.setText(mInterests);
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * When a user clicks the edit floating action button, a dialog will be shown where the user can edit their current name, email, major, and interests.
     * Once the user is done editing the information, the user will press the "Save" button in the dialog to commit their changes to Firebase.
     */
    @OnClick(R.id.profile_fab)
    public void editProfile() {
        final MaterialDialog editProfileDialog = new MaterialDialog.Builder(ProfileActivity.this)
                .title(editProfileDialogTitle)
                .customView(R.layout.edit_profile_dialog, true)
                .theme(Theme.DARK)
                .positiveText(save)
                .negativeText(cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog editProfileDialog, @NonNull DialogAction which) {
                        final EditText editName = ButterKnife.findById(editProfileDialog, R.id.edit_name);
                        final EditText editEmail = ButterKnife.findById(editProfileDialog, R.id.edit_email);
                        final Spinner majorDropdown = ButterKnife.findById(editProfileDialog, R.id.major_dropdown);
                        final EditText editInterests = ButterKnife.findById(editProfileDialog, R.id.edit_interests);

                        final String NEW_NAME = editName.getText().toString();
                        final String NEW_EMAIL = editEmail.getText().toString();
                        final String NEW_INTERESTS = editInterests.getText().toString();
                        final Major NEW_MAJOR = (Major) majorDropdown.getSelectedItem();

                        Firebase userRef = mRef.child(mSession.getLoggedInUsername());
                        HashMap<String, Object> updateValues = new HashMap<>();
                        updateValues.put("name", NEW_NAME);
                        updateValues.put("email", NEW_EMAIL);
                        updateValues.put("interests", NEW_INTERESTS);
                        if (NEW_MAJOR != Major.NONE) {
                            updateValues.put("major", NEW_MAJOR.toString());
                        }
                        userRef.updateChildren(updateValues); //Update Firebase with new values

                        /*Update the name and email that's stored in this Session*/
                        mName = NEW_NAME;
                        mEmail = NEW_EMAIL;
                        if (NEW_MAJOR != Major.NONE) {
                            mMajor = NEW_MAJOR;
                        }
                        mSession.updateSession(mName, mEmail, NEW_MAJOR.toString());

                        (ProfileActivity.this).passThrough(editName, editEmail, editInterests);
                        if (mMajor != null && mMajor != Major.NONE) {
                            (ProfileActivity.this).profileMajor.setText(mMajor.toString());
                        }
                    }
                }).build();

        if (editProfileDialog.getCustomView() != null) {
            final EditText editName = ButterKnife.findById(editProfileDialog, R.id.edit_name);
            final EditText editEmail = ButterKnife.findById(editProfileDialog, R.id.edit_email);
            final EditText editInterests = ButterKnife.findById(editProfileDialog, R.id.edit_interests);
            final View saveEdits = editProfileDialog.getActionButton(DialogAction.POSITIVE);

            StringHelper.emailWatcher(editEmail, saveEdits, invalidEmail);

            /*Need to override isEnabled so the user can't select the hint text in the spinner*/
            ArrayAdapter<Major> adapter = new ArrayAdapter<Major>(this, android.R.layout.simple_spinner_dropdown_item, Major.values()) {
                @Override
                public boolean isEnabled(int position) {
                    return position != 0; //Disabled "Select a major" item
                }
                @Override
                public boolean areAllItemsEnabled() {
                    return false;
                }
            };
            final AbsSpinner majorDropdown = ButterKnife.findById(editProfileDialog, R.id.major_dropdown);
            majorDropdown.setAdapter(adapter);
            editName.setText(mName);
            editEmail.setText(mEmail);
            if (mMajor != null) {
                majorDropdown.setSelection(((ArrayAdapter<Major>) majorDropdown.getAdapter()).getPosition(mMajor));
            }
            if (mInterests != null) {
                editInterests.setText(mInterests);
            }
        }
        editProfileDialog.show();
    }

    /**
     * When a user clicks the menu overflow icon and selects "Change Password", the user will be presented with a dialog asking for a new password and for them to confirm the new password.
     * Once the user is done changing the password, the user will press the "Save" button in the dialog to commit their changes to Firebase.
     */
    private void changePassword() {
        final MaterialDialog editPasswordDialog = new MaterialDialog.Builder(ProfileActivity.this)
                .title(editPasswordDialogTitle)
                .customView(R.layout.edit_password_dialog, true)
                .theme(Theme.DARK)
                .positiveText(save)
                .negativeText(cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull final MaterialDialog editPasswordDialog, @NonNull DialogAction which) {
                        final EditText editPasswordOld = ButterKnife.findById(editPasswordDialog, R.id.edit_password_old);
                        final EditText editPassword = ButterKnife.findById(editPasswordDialog, R.id.edit_password);
                        final EditText editPasswordConfirm = ButterKnife.findById(editPasswordDialog, R.id.edit_password_confirm);

                        final String editPasswordOldText = editPasswordOld.getText().toString();
                        final String editPasswordText = editPassword.getText().toString();
                        final String editPasswordConfirmText = editPasswordConfirm.getText().toString();

                        if (StringHelper.passwordMatch(editPassword, editPasswordConfirm)
                                && editPasswordText.length() != 0
                                && editPasswordConfirmText.length() != 0) {
                            mRef.changePassword(StringHelper.setUserWithDummyDomain(mUsername), editPasswordOldText, editPasswordConfirmText, new Firebase.ResultHandler() {
                                @Override
                                public void onSuccess() {
                                    ViewHelper.makeSnackbar(thisActivity, passwordChangeSuccess, Snackbar.LENGTH_LONG, accentColor, primaryTextLightColor).show();
                                    Log.v("Change password", "Success!!");
                                }
                                @Override
                                public void onError(FirebaseError firebaseError) {
                                    switch (firebaseError.getCode()) {
                                        case FirebaseError.INVALID_PASSWORD:
                                            ViewHelper.makeSnackbar(thisActivity, passwordIncorrect, Snackbar.LENGTH_LONG, accentColor, primaryTextLightColor).show();
                                            break;
                                    }
                                    Log.e("Change password", firebaseError.toString());
                                }
                            });
                        }
                    }
                }).build();

        final View saveAction = editPasswordDialog.getActionButton(DialogAction.POSITIVE);

        if (editPasswordDialog.getCustomView() != null) {
            final EditText editPasswordOld = ButterKnife.findById(editPasswordDialog, R.id.edit_password_old);
            final EditText editPassword = ButterKnife.findById(editPasswordDialog, R.id.edit_password);
            final EditText editPasswordConfirm = ButterKnife.findById(editPasswordDialog, R.id.edit_password_confirm);

            final TextWatcher passwordWatcher = StringHelper.passwordWatcher(editPasswordOld,
                    editPassword, editPasswordConfirm, saveAction, passwordMismatch);

            editPasswordOld.addTextChangedListener(passwordWatcher);
            editPassword.addTextChangedListener(passwordWatcher);
            editPasswordConfirm.addTextChangedListener(passwordWatcher);
        }
        editPasswordDialog.show();
        saveAction.setEnabled(false); //Disabled by default
    }

    /**
     * I wish I could tell you. If I put this up in the method where it is called, the text doesn't update. Moving it to it's own method works, however.
     * Allows us to update/refresh the ProfileActivity's data once a user edits the information.
     * @param editName is the field where the user edits their name
     * @param editEmail is the field where the user edits their email
     * @param editInterests is the field where the user edits their interests
     */
    private void passThrough(EditText editName, EditText editEmail, EditText editInterests) {
        profileName.setText(editName.getText().toString());
        profileEmail.setText(editEmail.getText().toString());
        profileInterests.setText(editInterests.getText().toString());
    }

    /**
     * Helper method that inits all of the Toolbar stuff.
     * Specifically:
     * sets Toolbar title, shows a back arrow for navigation, and handles what to do if a user presses the back button in the Toolbar.
     */
    private void initToolbar() {
        assert getSupportActionBar() != null;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(mUsername);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); //Simulate a system's "Back" button functionality.
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.change_password) {
            changePassword();
        }

        return super.onOptionsItemSelected(item);
    }
}
