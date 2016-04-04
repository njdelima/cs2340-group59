package ga.neerajdelima.themovieapp.model;

import android.app.Activity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ga.neerajdelima.themovieapp.model.network.AdminTask;
import ga.neerajdelima.themovieapp.model.network.BanTask;
import ga.neerajdelima.themovieapp.model.network.FetchTask;
import ga.neerajdelima.themovieapp.model.network.FetchUserListResponse;
import ga.neerajdelima.themovieapp.model.network.FetchUserListTask;
import ga.neerajdelima.themovieapp.model.network.LockTask;
import ga.neerajdelima.themovieapp.model.network.ProcessLoginResponse;
import ga.neerajdelima.themovieapp.model.network.ProcessLoginTask;

/**
 * Created by Neeraj on 2/15/16.
 */
public class UserModel {

    /*
     * Keeps track of the currently logged in user.
     * If no one's logged in it should be null.
     */
    private static User loggedInUser = null;

    /**
     * For hashing passwords.
     * Don't move plain text passwords around. hash it
     * as soon as you read from the text box.
     * @param stringToHash string to be converted to hashcode
     * @return hased password
     */
    public String md5(String stringToHash) {
        String hashedString = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(stringToHash.getBytes());
            hashedString = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            Log.d("NoSuchAlgorithmException", e.getMessage());
        }
        return hashedString;
    }
<<<<<<< HEAD

=======
>>>>>>> master
    /**
     * Process login activity
     * @param activity activity to be executed
     * @param username username
     * @param password password
     */
    public void processLogin(Activity activity, String username, String password) {
        ProcessLoginTask processLoginTask = new ProcessLoginTask(username, password);
        processLoginTask.delegate = (ProcessLoginResponse) activity;
        processLoginTask.execute();
    }
<<<<<<< HEAD

=======
>>>>>>> master
    /**
     * Sets the currently logged in user to be username
     * @param username logged in user's username
     */
    public void setLoggedInUser(String username) {
        new setLoggedInUserTask(username).execute();
    }
<<<<<<< HEAD

=======
>>>>>>> master
    /**
     * Get list of user
     * @param activity activity to be executed
     */
    public void getUserList(Activity activity) {
        FetchUserListTask fetchUserListTask = new FetchUserListTask();
        fetchUserListTask.delegate = (FetchUserListResponse) activity;
        fetchUserListTask.execute();
    }

<<<<<<< HEAD
=======
    /**
     * @return logged in user's UserModel
     */
    public User getLoggedInUser() {
        return UserModel.loggedInUser;
    }
    /**
     * @return logged in user's username
     */
    public String getLoggedInUsername() {
        return loggedInUser.getUsername();
    }
    /**
     * Update user's profile
     * @param username username
     * @param newUsername new username
     * @param newPassword new password
     * @param newFirstName new first name
     * @param newLastName new last name
     * @param newMajor new major
     */
    public void updateProfile(String username, String newUsername, String newPassword, String newFirstName, String newLastName, String newMajor) {
        Log.d("Checkpoint", "about to start updateprofileTask");
        new updateProfileTask(username, newUsername, newPassword, newFirstName, newLastName, newMajor).execute();
    }
    /**
     * Lock user
     * @param username username to be locked
     */
    public void lockUser(String username) {
        new LockTask(username, true).execute();
    }
    /**
     * Unlock user
     * @param username username to be unlocked
     */
    public void unlockUser(String username) {
        new LockTask(username, false).execute();
    }
    /**
     * Make user admin
     * @param username username to be admin
     */
    public void makeAdmin(String username) {
        new AdminTask(username, true).execute();
    }
    /**
     * Demote admin to user
     * @param username username to be demoted
     */
    public void removeAdmin(String username) {
        new AdminTask(username, false).execute();
    }
    /**
     * Ban user
     * @param username username to be banned
     */
    public void banUser(String username) {
        new BanTask(username, true).execute();
    }
    /**
     * Unban user
     * @param username username to be unbanned
     */
    public void unbanUser(String username) {
        new BanTask(username, false).execute();
    }

>>>>>>> master
    /*
     * this task queries the database for the @param username
     * and gets the rest of the information about the user.
     * Sets the loggedInUser variable accordingly
     */
    private class setLoggedInUserTask extends FetchTask {

        private JSONObject data;
<<<<<<< HEAD

=======
>>>>>>> master
        /**
         * Constructor of setLoggedInUserTask
         * @param username username
         */
        public setLoggedInUserTask(String username) {
            super("http://128.61.104.207:2340/api/users/fetch.php"); //Make a FetchTask
            data = new JSONObject();
            try {
                data.put("username", username);
            } catch (JSONException e) {
                //Log.d("JSONException", e.getStackTrace().toString());
                return;
            }
        }

        @Override
        protected Object doInBackground(Object... args) {
            try {
                Log.d("json to send", data.toString());
                sendPostData(data); //Sends a POST request with the JSON containing the username
                JSONObject response = new JSONObject(getInputString()); // get the server response
                Log.d("Json received", response.toString());
                //Create a new User from the received data
                User user = new User(response.getString("username"), response.getString("password"),
                                        response.getString("first_name"), response.getString("last_name"),
                                        response.getString("major"), response.optBoolean("locked"),
                                        response.optBoolean("banned"), response.optBoolean("admin"));
                UserModel.loggedInUser = user;
            } catch (JSONException e) {
                Log.d("JsonException", e.getMessage());
            }
            return null;
        }
    }

    /**
     * @return logged in user's UserModel
     */
    public User getLoggedInUser() {
        return UserModel.loggedInUser;
    }
    /**
     * @return logged in user's username
     */
    public String getLoggedInUsername() {
        return loggedInUser.getUsername();
    }

    /**
     * Update user's profile
     * @param username username
     * @param newUsername new username
     * @param newPassword new password
     * @param newFirstName new first name
     * @param newLastName new last name
     * @param newMajor new major
     */
    public void updateProfile(String username, String newUsername, String newPassword, String newFirstName, String newLastName, String newMajor) {
        Log.d("Checkpoint", "about to start updateprofileTask");
        new updateProfileTask(username, newUsername, newPassword, newFirstName, newLastName, newMajor).execute();
    }

    /**
     * Lock user
     * @param username username to be locked
     */
    public void lockUser(String username) {
        new LockTask(username, true).execute();
    }
    /**
     * Unlock user
     * @param username username to be unlocked
     */
    public void unlockUser(String username) {
        new LockTask(username, false).execute();
    }
    /**
     * Make user admin
     * @param username username to be admin
     */
    public void makeAdmin(String username) {
        new AdminTask(username, true).execute();
    }
    /**
     * Demote admin to user
     * @param username username to be demoted
     */
    public void removeAdmin(String username) {
        new AdminTask(username, false).execute();
    }
    /**
     * Ban user
     * @param username username to be banned
     */
    public void banUser(String username) {
        new BanTask(username, true).execute();
    }
    /**
     * Unban user
     * @param username username to be unbanned
     */
    public void unbanUser(String username) {
        new BanTask(username, false).execute();
    }

    /*
     * This task updates the user in the database with the new information
     *
     */
    private class updateProfileTask extends FetchTask {

        private JSONObject data;
<<<<<<< HEAD

        /**
         * Constructor of updateProfileTask
=======
        /**
         * Update user's profile
>>>>>>> master
         * @param username username
         * @param newUsername new username
         * @param newPassword new password
         * @param newFirstName new first name
         * @param newLastName new last name
         * @param newMajor new major
         */
        public updateProfileTask(String username, String newUsername, String newPassword, String newFirstName, String newLastName, String newMajor) {
            super("http://128.61.104.207:2340/api/users/update.php"); // Create a FetchTask
            Log.d("Checkpoint", "inside updateProfileTask constructor");
            data = new JSONObject();
            // Put all the information into a JSON
            try {
                data.put("username", username);
                data.put("new_username", newUsername);
                data.put("new_password", newPassword);
                data.put("new_first_name",newFirstName);
                data.put("new_last_name",newLastName);
                data.put("new_major", newMajor);
            } catch (JSONException e) {
                Log.d("JSONException", e.getStackTrace().toString());
            }
        }
        @Override
        protected Object doInBackground(Object... args) {
            Log.d("About to send json", data.toString());
            sendPostData(data); // POST the JSON
            Log.d("response", getResponseMessage()); // should be 'OK'
            try {
                setLoggedInUser(data.getString("username")); //update the loggedInUser's information
            } catch (JSONException e) {
                Log.d("JSONException", e.getStackTrace().toString());
            }
            return null;
        }
    }
}
