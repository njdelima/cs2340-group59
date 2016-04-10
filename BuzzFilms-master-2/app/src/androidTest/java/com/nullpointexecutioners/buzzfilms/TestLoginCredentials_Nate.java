package com.nullpointexecutioners.buzzfilms;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.view.View;

import com.nullpointexecutioners.buzzfilms.activities.MainActivity;
import com.nullpointexecutioners.buzzfilms.helpers.SessionManager;

import org.hamcrest.Matcher;
import org.hamcrest.core.IsAnything;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class TestLoginCredentials_Nate extends AndroidTestCase {

    Activity activity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = mMainActivityRule.getActivity();
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

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testVerifyCredentials() {
        SessionManager session = SessionManager.getInstance(activity.getApplicationContext());

        assertTrue(session.checkLogin());
        assertTrue(session.checkAdmin());
        assertEquals(session.getLoggedInUsername(), "admin");
        assertEquals(session.getLoggedInEmail(), "nateg@gatech.edu");
    }

//    @Rule
//    public ActivityTestRule<WelcomeActivity> mWelcomeActivityRule = new ActivityTestRule<>(WelcomeActivity.class);
//
//    @Test
//    public void testLogin() throws InterruptedException {
//        onView(withId(R.id.login_username)).perform(click()).perform(typeText("admin"));
//        onView(withId(R.id.login_password)).perform(click()).perform(typeText("pass"), closeSoftKeyboard());
//        onView(withId(R.id.login_username)).check(matches(withText("admin")));
//        onView(withId(R.id.login_password)).check(matches(withText("pass")));
//
//        onView(withId(R.id.login_button)).check(matches(isClickable()));
//        onView(withId(R.id.login_button)).perform(click());
//    }

    /**
     * Perform action of waiting for a specific time. Useful when you need
     * to wait for animations to end and Espresso fails at waiting.
     * E.g.:
     * onView(isRoot()).perform(waitAtLeast(Sampling.SECONDS_15));
     * @param millis delay in ms
     * @return ViewAction
     */
    public static ViewAction waitAtLeast(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return new IsAnything<>();
            }

            @Override
            public String getDescription() {
                return "wait for at least " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    /**
     * Perform action of waiting until UI thread is free.
     * E.g.:
     * onView(isRoot()).perform(waitUntilIdle());
     * @return ViewAction
     */
    public static ViewAction waitUntilIdle() {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return new IsAnything<>();
            }

            @Override
            public String getDescription() {
                return "wait until UI thread is free";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
            }
        };
    }
}