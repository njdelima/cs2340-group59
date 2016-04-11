package ga.neerajdelima.themovieapp;

import ga.neerajdelima.themovieapp.HomeActivity;
import ga.neerajdelima.themovieapp.model.Movie;

import java.util.List;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * TopMoviesResponseTest.java
 *
 * @author Emma Staniforth
 * @version 1.0
 */
public class TopMoviesResponseTest {

    private HomeActivity home;
    private List<Movie> movieList;
    private List<String> movieResults;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        home = new HomeActivity();

        movieList = new ArrayList<>();
        movieList.add(new Movie("1", "Shrek", 8, 1));
        movieList.add(new Movie("2", "Star Wars", 98, 10));
        movieList.add(new Movie("3", "Inception", 84, 9));
        movieList.add(new Movie("4", "Zootopia", 30, 3));
        movieList.add(new Movie("5", "Mean Girls", 73, 9));

        movieResults = new ArrayList<>();
        movieResults.add("Shrek,Average Rating: 8");
        movieResults.add("Star Wars,Average Rating: 9");
        movieResults.add("Inception,Average Rating: 9");
        movieResults.add("Zootopia,Average Rating: 10");
        movieResults.add("Mean Girls,Average Rating: 8");

        movieResults.add("Shrek");
        movieResults.add("Star Wars");
        movieResults.add("Inception");
        movieResults.add("Zootopia");
        movieResults.add("Mean Girls");
    }

    @Test(timeout = TIMEOUT)
    public void test01ResultsIsNull() {
        List<String> results = home.onTopMoviesResponse(null);
        assertNotNull(results);
        assertEquals("No recommendations by null majors", results.get(0));
    }

    @Test(timeout = TIMEOUT)
    public void test02ResultsIsValid() {
        List<String> results = home.onTopMoviesResponse(movieList);
        assertNotNull(results);
        assertEquals(10, results.size());
        assertEquals(movieResults, results);
    }
}
