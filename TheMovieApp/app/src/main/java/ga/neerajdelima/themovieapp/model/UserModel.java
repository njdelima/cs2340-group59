package ga.neerajdelima.themovieapp.model;

import java.util.ArrayList;
import android.util.Log;

/**
 * Created by Neeraj on 2/15/16.
 */
public class UserModel {
    static ArrayList<User> users = new ArrayList<User>();

    public boolean checkLogin(String username, String password) {
        User toCheck = new User(username, password);
        return users.contains(toCheck);
    }

    public void addUser(String username, String password) {
        User toAdd = new User(username, password);
        users.add(toAdd);
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
