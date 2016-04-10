package com.nullpointexecutioners.buzzfilms;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.widget.EditText;

import com.nullpointexecutioners.buzzfilms.activities.ProfileActivity;
import com.nullpointexecutioners.buzzfilms.helpers.SessionManager;
import com.nullpointexecutioners.buzzfilms.helpers.StringHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Jonathan Hooper on 4/6/16.
 */
@RunWith(AndroidJUnit4.class)
public class TestPasswordMatch_Jonathan extends AndroidTestCase {
    @Rule
    public ActivityTestRule<ProfileActivity> mProfileActivityRule = new ActivityTestRule<>(ProfileActivity.class);

    Activity activity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = mProfileActivityRule.getActivity();
    }

    public void tearDown() throws Exception {
        goBackN();
        super.tearDown();
    }

    /**
     * This will press the back button several times in order to clear the stack of Activities
     */
    private void goBackN() {
        final int N = 10; // how many times to hit back button
        try {
            for (int i = 0; i < N; i++)
                Espresso.pressBack();
        } catch (Exception e) {
        }
    }
    // Test only works once as the name and email will be the same the second time.
    @Test
    public void testPassWordMatch() throws InterruptedException {
        SessionManager session = SessionManager.getInstance(activity.getApplicationContext());
        EditText pw1 = new EditText(activity.getApplicationContext());
        EditText pw2 = new EditText(activity.getApplicationContext());
        pw1.setText("fish");
        pw1.setText("lancer");
        assertTrue(StringHelper.passwordMatch(pw1,pw1));
        assertTrue(StringHelper.passwordMatch(pw2,pw2));
        assertFalse(StringHelper.passwordMatch(pw1,pw2));
    }

}
