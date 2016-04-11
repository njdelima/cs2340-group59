package ga.neerajdelima.themovieapp;

import ga.neerajdelima.themovieapp.model.Movie;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Eduardo on 4/11/2016.
 */
public class MovieCompareToTest {
    @Test
    public void createNewMovie() {
        Movie movie = new Movie("tt0468569","The Dark Knight", 10, 4);
        assertEquals("tt0468569", movie.getImdbID());
        assertEquals("The Dark Knight", movie.getTitle());
        assertEquals(10, movie.getTotalRating());
        assertEquals(4, movie.getRatingCount());

    }
    @Test
    public void moviesSameRating() {
        Movie movie1 = new Movie("tt0468569","The Dark Knight", 10, 4);
        Movie movie2 = new Movie("tt0468569","The Dark Knight", 10, 4);
        int num = 0;
        assertEquals(num, movie1.compareTo(movie2));
    }

    @Test
    public void moviesComparedLowerRating() {
        Movie movie1 = new Movie("tt0139539","Batman vs Superman, Dawn of Justice", 3, 5);
        Movie movie2 = new Movie("tt0468569","The Dark Knight", 10, 4);
        int num = -1;
        assertEquals(num, movie1.compareTo(movie2));
    }

    @Test
    public void moviesComparedHigherRating() {
        Movie movie1 = new Movie("tt0468569","The Dark Knight", 10, 4);
        Movie movie2 = new Movie("tt0139539","Batman vs Superman, Dawn of Justice", 3, 5);
        int num = 1;
        assertEquals(num, movie1.compareTo(movie2));
    }

    @Test
    public void sameMovie() {
        Movie movie1 = new Movie("tt0468569","The Dark Knight", 10, 4);
        assertEquals(0, movie1.compareTo(movie1));
    }
}
