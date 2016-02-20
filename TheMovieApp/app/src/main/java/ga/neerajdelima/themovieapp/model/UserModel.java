package ga.neerajdelima.themovieapp.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import android.util.Log;
import android.os.AsyncTask;
import org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Neeraj on 2/15/16.
 */
public class UserModel {
    static ArrayList<User> users = new ArrayList<User>();

    private class UserTask extends AsyncTask {
        @Override
        protected String doInBackground(Object... args) {
            String requestURL = (String) args[0];
            String jsonData = (String) args[1];
            try {
                URL url = new URL(requestURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("charset", "utf-8");
                connection.setRequestProperty("Content-Length", Integer.toString(jsonData.length()));
                connection.setUseCaches(false);

                byte[] postData = jsonData.getBytes(StandardCharsets.UTF_8);
                connection.getOutputStream().write(postData);
                return connection.getResponseMessage();
            } catch (MalformedURLException e) {
                Log.d("Malformed URL", e.getMessage());
            } catch (IOException e) {
                Log.d("IOException", e.getMessage());
            }
            return null;
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
        String data = "{\"username\":\"tanay\",\"password\",\"12345\"}";
        new UserTask().execute("http://128.61.104.207:2340/api/users/add.php", data);
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
