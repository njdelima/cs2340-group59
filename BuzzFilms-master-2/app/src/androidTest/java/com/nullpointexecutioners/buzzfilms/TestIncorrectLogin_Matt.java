package com.nullpointexecutioners.buzzfilms;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.nullpointexecutioners.buzzfilms.activities.WelcomeActivity;
import com.nullpointexecutioners.buzzfilms.helpers.SessionManager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class TestIncorrectLogin_Matt extends AndroidTestCase {

    @Rule
    public ActivityTestRule<WelcomeActivity> mWelcomeActivityRule = new ActivityTestRule<>(WelcomeActivity.class);

    Activity activity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = mWelcomeActivityRule.getActivity();
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

    @Test
    public void testLogin() throws InterruptedException {

        SessionManager session = SessionManager.getInstance(activity.getApplicationContext());
        session.logoutUser();

        onView(withId(R.id.login_username)).perform(click()).perform(typeText("admin"));
        onView(withId(R.id.login_password)).perform(click()).perform(typeText("wrongpassword"), closeSoftKeyboard());
        onView(withId(R.id.login_username)).check(matches(withText("admin")));
        onView(withId(R.id.login_password)).check(matches(withText("wrongpassword")));
        onView(withId(R.id.login_button)).check(matches(isClickable()));
        onView(withId(R.id.login_button)).perform(click());


        assertEquals(true, session.getLoggedInName() == null);
        assertEquals(true, session.getLoggedInEmail() == null);
        assertEquals(true, session.getLoggedInMajor() == null);
        assertEquals(true, session.getLoggedInUsername() == null);
    }
}