package ga.neerajdelima.themovieapp.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;

import java.security.MessageDigest;

import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import ga.neerajdelima.themovieapp.HomeActivity;
import ga.neerajdelima.themovieapp.R;
import ga.neerajdelima.themovieapp.model.network.FetchTask;

/**
 * Created by Neeraj on 2/15/16.
 */
public class UserModel {

    static User loggedInUser;
    static String loggedInUsername;

    public UserModel() {
        loggedInUser = null;
    }

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

    public void setLoggedInUser(String username) {
        new setLoggedInUserTask(username).execute();
    }

    private class setLoggedInUserTask extends FetchTask {

        JSONObject data;

        public setLoggedInUserTask(String username) {
            super("http://128.61.104.207:2340/api/users/fetch.php");
            data = new JSONObject();
            try {
                data.put("username", username);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected User doInBackground(Object... args) {
            try {
                Log.d("json to send", data.toString());
                sendPostData(data);
                JSONObject response = new JSONObject(getInputString());
                Log.d("Json received", response.toString());
                User user = new User(response.getString("username"), response.getString("password"),
                                        response.getString("first_name"), response.getString("last_name"),
                                        response.getString("major"));
                return user;
            } catch (JSONException e) {
                Log.d("JsonException", e.getMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Object u) {
            User user = (User) u;
            UserModel.loggedInUser = user;
            Log.d("Usermodel Logged in user = ", UserModel.loggedInUser.toString());
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public String getLoggedInUsername() {
        return loggedInUsername;
    }
    public void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    public void updateProfile(String username, String newUsername, String newPassword, String newFirstName, String newLastName, String newMajor) {
        new updateProfileTask(username, newUsername, newPassword, newFirstName, newLastName, newMajor).execute();
    }

    private class updateProfileTask extends FetchTask {

        JSONObject data;

        public updateProfileTask(String username, String newUsername, String newPassword, String newFirstName, String newLastName, String newMajor) {
            super("http://128.61.104.207:2340/api/users/update.php");
            data = new JSONObject();
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
            sendPostData(data);
            return null;
        }
    }
}
