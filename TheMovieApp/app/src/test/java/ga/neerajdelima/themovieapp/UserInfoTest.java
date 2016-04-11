package ga.neerajdelima.themovieapp;

import android.test.InstrumentationTestCase;

import java.util.List;

import ga.neerajdelima.themovieapp.model.User;
import ga.neerajdelima.themovieapp.model.UserModel;
/**
 * Created by minholee on 4/10/16.
 * Test User.java
 */

public class UserInfoTest extends InstrumentationTestCase {
    UserModel userModel;
    List<User> users;

    public void testUserInfo() throws Exception {
        userModel = new UserModel();
        userModel.setLoggedInUser("minho");
        Thread.sleep(10000);
        users.add(userModel.getLoggedInUser());
        userModel = new UserModel();
        userModel.setLoggedInUser("emma");
        users.add(userModel.getLoggedInUser());
        userModel = new UserModel();
        userModel.setLoggedInUser("eduardo");
        users.add(userModel.getLoggedInUser());
        userModel = new UserModel();
        userModel.setLoggedInUser("komal");
        users.add(userModel.getLoggedInUser());
        userModel = new UserModel();
        userModel.setLoggedInUser("neeraj");
        users.add(userModel.getLoggedInUser());
        assertEquals(true, users.get(0).isAdmin());
        assertEquals(true, users.get(0).isBanned());
        assertEquals(true, users.get(0).isLocked());
        assertEquals(true, users.get(1).isAdmin());
        assertEquals(true, users.get(1).isBanned());
        assertEquals(true, users.get(1).isLocked());
        assertEquals(true, users.get(2).isAdmin());
        assertEquals(true ,users.get(2).isBanned());
        assertEquals(true ,users.get(2).isLocked());
        assertEquals(true, users.get(3).isAdmin());
        assertEquals(true ,users.get(3).isBanned());
        assertEquals(true ,users.get(3).isLocked());
        assertEquals(true, users.get(4).isAdmin());
        assertEquals(true ,users.get(4).isBanned());
        assertEquals(true ,users.get(4).isLocked());
    }



}
