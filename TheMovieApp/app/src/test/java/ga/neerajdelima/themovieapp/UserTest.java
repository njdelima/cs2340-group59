package ga.neerajdelima.themovieapp;

import ga.neerajdelima.themovieapp.model.User;
import ga.neerajdelima.themovieapp.model.Movie;

import org.junit.Test;


import static org.junit.Assert.*;

/**
 * Created by komalhirani on 4/10/16.
 * Test User.java
 */


public class UserTest {

    @Test
    public void createNewUserProfile() {
        User testUser = new User("khirani", "1234", "", "", "", false, false, false);
        assertEquals("khirani", testUser.getUsername());
        assertEquals("1234", testUser.getPassword());
        assertEquals("", testUser.getFirstName());
        assertEquals("", testUser.getLastName());
        assertEquals("", testUser.getMajor());

    }

    @Test
    public void equalsAnotherUser() {
        User testUser1 = new User("khirani", "1234", "", "", "", false, false, false);
        User testUser2 = new User("khirani", "1234", "", "", "", false, false, false);
        assertTrue(testUser1.equals(testUser2));
    }

    @Test
    public void doesNotEqualMovie() {
        User testUser = new User("khirani", "1234", "", "", "", false, false, false);
        Movie testMovie = new Movie("title", "title", 10, 10);
        assertFalse(testUser.equals(testMovie));
    }

    @Test
    public void doesObjectEqualNull() {
        User testUser = new User("khirani", "1234", "", "", "", false, false, false);
        assertFalse(testUser.equals(null));
    }

    @Test
    public void doesNotEqualAnotherUser() {
        User testUser1 = new User("khirani", "1234", "", "", "", false, false, false);
        User testUser2 = new User("khirani6", "1234", "", "", "", false, false, false);
        assertFalse(testUser1.equals(testUser2));
    }
    @Test
    public void equalsItself() {
        User testUser = new User("khirani", "1234", "", "", "", false, false, false);
        assertTrue(testUser.equals(testUser));
    }

}
