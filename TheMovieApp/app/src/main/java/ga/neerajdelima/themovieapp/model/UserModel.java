package ga.neerajdelima.themovieapp.model;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import android.util.Log;
import android.os.AsyncTask;

import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Neeraj on 2/15/16.
 */
public class UserModel {
    static ArrayList<User> users = new ArrayList<User>();

    private abstract class BackgroundTask extends AsyncTask {
        protected String sendPostData(String requestURL, JSONObject jsonData) {
            try {
                URL url = new URL(requestURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                byte[] postData = jsonData.toString().getBytes();
                connection.getOutputStream().write(postData);
                return connection.getResponseMessage();
            } catch (MalformedURLException e) {
                Log.d("Malformed URL Exception", e.getMessage());
            } catch (IOException e) {
                Log.d("IOException", e.getMessage());
            }
            return null;
        }
    }

    private class UserTask extends BackgroundTask {
        @Override
        protected String doInBackground(Object... args) {
            String requestURL = (String) args[0];
            JSONObject jsonData = (JSONObject) args[1];

            String temp = sendPostData(requestURL, jsonData);
            Log.d("response", temp);
            return temp;
        }

        protected void onPostExecute(String response) {
            Log.d("Response", response);
        }
    }

    public boolean checkLogin(String username, String password) {
        User toCheck = new User(username, password);
        return users.contains(toCheck);
    }

    public void addUser(String username, String password) {
        try {
            JSONObject data = new JSONObject();
            data.put("username", username);
            data.put("password", password);
            new UserTask().execute("http://128.61.104.207:2340/api/users/add.php", data);
        } catch (JSONException e) {
            Log.d("JSONException", e.getMessage());
        }

        //User toAdd = new User(username, password);
        //users.add(toAdd);
    }

    public void logAllUsers() {
        for (User user : users) {
            Log.d("username", user.getUsername());
            Log.d("password", user.getPassword());
        }
    }

    public void logUserIn(String username) {
        getUser(username).logIn();
    }

    public void logUserOut(String username) {
        getUser(username).logOut();
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    public String getLoggedInUsername() {
        for(User user : users) {
            if (user.isLoggedIn()) return user.getUsername();
        }
        return null;
    }
    public User getLoggedInUser() {
        for (User user : users) {
            if (user.isLoggedIn()) return user;
        }
        return null;
    }

    public void updateProfile(String firstName, String lastName, String major) {
        User loggedInUser = getLoggedInUser();

        loggedInUser.setFirstName(firstName);
        loggedInUser.setLastName(lastName);
        loggedInUser.setMajor(major);
    }

}
