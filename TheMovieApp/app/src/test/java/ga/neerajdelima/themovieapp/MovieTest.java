package ga.neerajdelima.themovieapp;

import ga.neerajdelima.themovieapp.model.Movie;
import ga.neerajdelima.themovieapp.model.User;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * TopMoviesResponseTest.java
 *
 * @author Emma Staniforth
 * @version 1.0
 */
public class MovieTest {

    @Test
    public void testNewMovie() {
        Movie movie = new Movie("1", "Shrek", 38, 4);
        assertEquals("1", movie.getImdbID());
        assertEquals("Shrek", movie.getTitle());
        assertEquals(38, movie.getTotalRating());
        assertEquals(4, movie.getRatingCount());
    }

    @Test
    public void testMovieEquals() {
        Movie movie1 = new Movie("1", "Shrek", 38, 4);
        Movie movie2 = new Movie("1", "Shrek", 38, 4);
        assertTrue(movie1.equals(movie2));
        assertTrue(movie1.equals(movie1));
    }

    @Test
    public void testMovieDoesNotEqual() {
        Movie movie1 = new Movie("1", "Shrek", 38, 4);
        Movie movie2 = new Movie("1", "Star Wars", 50, 5);
        assertFalse(movie1.equals(movie2));
    }

    @Test
    public void testMovieDoesNotEqualUser() {
        Movie movie = new Movie("1", "Shrek", 38, 4);
        User user = new User("user", "pass", "first", "last",
                "major", false, false, false);
        assertFalse(movie.equals(user));
    }

    @Test
    public void testMovieDoesNotEqualNull() {
        Movie movie = new Movie("1", "Shrek", 38, 4);
        assertFalse(movie.equals(null));
    }
}
