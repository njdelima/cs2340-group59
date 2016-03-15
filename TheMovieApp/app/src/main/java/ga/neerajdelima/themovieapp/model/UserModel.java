package ga.neerajdelima.themovieapp.model;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

import java.security.MessageDigest;

import android.app.Activity;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import ga.neerajdelima.themovieapp.model.network.FetchTask;
import ga.neerajdelima.themovieapp.model.network.FetchUserListResponse;
import ga.neerajdelima.themovieapp.model.network.FetchUserListTask;
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
    static User loggedInUser = null;

    /*
     * For hashing passwords.
     * Don't move plain text passwords around. hash it
     * as soon as you read from the text box.
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

    public void processLogin(Activity activity, String username, String password) {
        ProcessLoginTask processLoginTask = new ProcessLoginTask(username, password);
        processLoginTask.delegate = (ProcessLoginResponse) activity;
        processLoginTask.execute();
    }
    // Sets the currently logged in user to be @param username
    public void setLoggedInUser(String username) {
        new setLoggedInUserTask(username).execute();
    }

    public void getUserList(Activity activity) {
        FetchUserListTask fetchUserListTask = new FetchUserListTask();
        fetchUserListTask.delegate = (FetchUserListResponse) activity;
        fetchUserListTask.execute();
    }

    /*
     * this task queries the database for the @param username
     * and gets the rest of the information about the user.
     * Sets the loggedInUser variable accordingly
     */
    private class setLoggedInUserTask extends FetchTask {

        JSONObject data;

        public setLoggedInUserTask(String username) {
            super("http://128.61.104.207:2340/api/users/fetch.php"); //Make a FetchTask
            data = new JSONObject();
            try {
                data.put("username", username);
            } catch (JSONException e) {
                e.printStackTrace();
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

    public User getLoggedInUser() {
        return UserModel.loggedInUser;
    }

    public String getLoggedInUsername() {
        return loggedInUser.getUsername();
    }


    public void updateProfile(String username, String newUsername, String newPassword, String newFirstName, String newLastName, String newMajor) {
        Log.d("Checkpoint", "about to start updateprofileTask");
        new updateProfileTask(username, newUsername, newPassword, newFirstName, newLastName, newMajor).execute();
    }

    /*
     * This task updates the user in the database with the new information
     *
     */
    private class updateProfileTask extends FetchTask {

        JSONObject data;

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
                e.printStackTrace();
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
                e.printStackTrace();
            }
            return null;
        }
    }
}
