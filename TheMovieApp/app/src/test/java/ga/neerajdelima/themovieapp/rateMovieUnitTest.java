

package ga.neerajdelima.themovieapp;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;

import java.lang.Override;

import ga.neerajdelima.themovieapp.model.RatingsModel;
import ga.neerajdelima.themovieapp.model.UserModel;
import ga.neerajdelima.themovieapp.model.network.FetchUserListTask;
import ga.neerajdelima.themovieapp.model.network.FetchUserListResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class rateMovieUnitTest {

    RatingsModel ratingsModel;

    @Before
    public void setUp() {

        ratingsModel = new RatingsModel();
        userModel.
    }

    @Override
    public void onFetchUserListComplete(List<User> userList) {
        Log.d()
    }


    @Test
    public void testNoFilterTopMovies() {
        assertEquals(noFilterExpected, rl.getTopMovies("null", "null"));
    }

    @Test
    public void testNoFilterIgnoreParameter() {
        assertEquals(noFilterExpected, rl.getTopMovies("null", "major"));
    }

    @Test
    public void testNullParameter() {
        assertEquals(noFilterExpected, rl.getTopMovies("null", null));
    }

    @Test
    public void testMajorFilter() {
        assertEquals(csFilterExpected, rl.getTopMovies("major", "CS"));
    }

    @Test
    public void testOtherMajorFilter() {
        assertEquals(eeFilterExpected, rl.getTopMovies("major", "EE"));
    }

    @Test
    public void testNoMatchingMovies() {
        assertNull(rl.getTopMovies("major", "ME"));
    }

    @Test
    public void testNullParameterWithFilter() {
        assertNull(rl.getTopMovies("major", null));
    }

    @Test
    public void testFakeFilter() {
        assertNull(rl.getTopMovies("bogus", null));
    }

    @Test
    public void testNoRatingsButMovies() {
        rl.getRatings().clear();
        assertNull(rl.getTopMovies("null", "null"));
    }

    @Test
    public void testNoMoviesButRatings() {
        rl.getMovies().clear();
        assertEquals(noFilterExpected, rl.getTopMovies("null", "null"));
    }

    @Test
    public void testNoMoviesNoRatings() {
        rl.getMovies().clear();
        rl.getRatings().clear();
        assertNull(rl.getTopMovies("null", "null"));
    }

}