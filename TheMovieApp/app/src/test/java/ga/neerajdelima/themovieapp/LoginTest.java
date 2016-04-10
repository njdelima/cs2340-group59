package ga.neerajdelima.themovieapp;

import android.test.InstrumentationTestCase;

import org.json.JSONObject;

import java.util.List;

import ga.neerajdelima.themovieapp.model.User;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.ProcessLoginTask;

public class LoginTest extends InstrumentationTestCase {
    User user;
    UserModel userModel;
    JSONObject data;
    LoginActivity loginActivity;
    List<User> userModels;
    ProcessLoginTask processLoginTask;

    protected void setUp() throws Exception {
        super.setUp();
        System.out.print("EEE");
        userModel.setLoggedInUser("jae");
        Thread.sleep(10000);
        userModel.getLoggedInUser();
        userModel.processLogin(loginActivity, "jae", "1");
        userModels.add(userModel.getLoggedInUser());
        userModel.processLogin(loginActivity, "leepogi", "akals1168");
        userModels.add(userModel.getLoggedInUser());
        userModel.processLogin(loginActivity, "leepogii", "akals1168");
        userModels.add(userModel.getLoggedInUser());
        userModel.processLogin(loginActivity, "leepogiii", "akals1168");
        userModels.add(userModel.getLoggedInUser());
        testLogin();
    }

    public void testLogin() throws Exception {
        //user = new User("1", "1", "M", "L", "Computer Science", false, false, false);
        System.out.print("EEE");
        assertEquals(false, userModels.get(0).isAdmin());
        assertEquals(true, userModels.get(0).isLocked());
        assertEquals(false, userModels.get(0).isBanned());
        assertEquals(false, userModels.get(1).isAdmin());
        assertEquals(false, userModels.get(1).isLocked());
        assertEquals(false, userModels.get(1).isBanned());
        assertEquals(true, userModels.get(2).isAdmin());
        assertEquals(false, userModels.get(2).isLocked());
        assertEquals(false, userModels.get(2).isBanned());
        assertEquals(true, userModels.get(3).isAdmin());
        assertEquals(false, userModels.get(3).isLocked());
        assertEquals(true, userModels.get(3).isBanned());

        //userModel.getLoggedInUser().setAdmin(true);
//        assertEquals(new Integer(0), loginTest.getLoggedInUser().isAdmin());
    }
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}