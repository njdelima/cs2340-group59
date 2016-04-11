package ga.neerajdelima.themovieapp;

/**
 * Created by komalhirani on 4/11/16.
 */


import ga.neerajdelima.themovieapp.ResultActivity;

import android.support.test.InstrumentationRegistry;
//import android.test.AndroidTestCase;
import android.test.ActivityInstrumentationTestCase2;
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
public class MovieRatingTest {

    @Rule
            public ActivityTestRule<ResultActivity> mResultActivityRule = new ActivityTestRule<ResultActivity>(ResultActivity.class);

    Activity activity;
    int totalRating;
    int totalCount;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testVerifyActualInt() {

    }
}
