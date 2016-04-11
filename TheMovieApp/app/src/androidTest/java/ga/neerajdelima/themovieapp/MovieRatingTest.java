package ga.neerajdelima.themovieapp;

/**
 * Created by komalhirani on 4/11/16.
 */


import ga.neerajdelima.themovieapp.ResultActivity;
import android.test.AndroidTestCase;
import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by komalhirani on 4/10/16.
 * Test on onMovieRatingResponse method
 */
@RunWith(AndroidJUnit4.class)
public class MovieRatingTest extends AndroidTestCase{

    @Rule
            public ActivityTestRule<ResultActivity> mResultActivityRule = new ActivityTestRule<ResultActivity>(ResultActivity.class);

    Activity activity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = mResultActivityRule.getActivity();
        /*int totalRating;
        int totalCount;*/

    }

    @Test
    public void testVerifyActualInt() {
        assertEquals(false, );
    }
}
