package ga.neerajdelima.themovieapp;

import android.test.InstrumentationTestCase;

import org.junit.Test;

import java.util.ArrayList;
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

    public void setUp() {
        users = new ArrayList<>();
        users.add(new User("minho","pass","min","lee","Computer Science", false, false, true));
        users.add(new User("komal","word","kimal","lee","Industrial Engineering", false, true, true));
        users.add(new User("neeraj","pass","neeraj","super","Applied Mathematics", true, false, false));
        users.add(new User("edu","word","edu","champ","Computer Science", false, true, false));
        users.add(new User("emma","pass","emma","watson","Aerospace Engineering", true, false, false));
        testUserInfo();
    }
    @Test
    public void testUserInfo() {
        assertEquals(false, users.get(0).isLocked());
        assertEquals(false, users.get(0).isBanned());
        assertEquals(true, users.get(0).isAdmin());
        users.get(0).setLocked(true);
        assertEquals(true, users.get(0).isLocked()); // locked
        assertEquals(false, users.get(1).isLocked());
        assertEquals(true, users.get(1).isBanned());
        users.get(1).setBanned(false);
        assertEquals(false, users.get(1).isBanned());
        assertEquals(true, users.get(1).isAdmin());
        assertEquals(true, users.get(2).isLocked());
        users.get(2).setLocked(false);
        assertEquals(false, users.get(2).isLocked());
        assertEquals(false, users.get(2).isBanned());
        assertEquals(false, users.get(2).isAdmin());
        users.get(2).setAdmin(true);
        assertEquals(true, users.get(2).isAdmin());
        assertEquals(false, users.get(3).isLocked());
        assertEquals(true, users.get(3).isBanned());
        assertEquals(false, users.get(3).isAdmin());
        assertEquals(true, users.get(4).isLocked());
        users.get(4).setLocked(false);
        assertEquals(false, users.get(4).isLocked());
        assertEquals(false, users.get(4).isBanned());
        assertEquals(false, users.get(4).isAdmin());
        users.get(4).setAdmin(true);
        assertEquals(true, users.get(4).isAdmin());
    }



}
