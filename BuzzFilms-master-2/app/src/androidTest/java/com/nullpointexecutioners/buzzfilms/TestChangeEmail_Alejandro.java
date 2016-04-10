package com.nullpointexecutioners.buzzfilms;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import com.nullpointexecutioners.buzzfilms.activities.ProfileActivity;
import com.nullpointexecutioners.buzzfilms.activities.WelcomeActivity;
import com.nullpointexecutioners.buzzfilms.helpers.SessionManager;

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
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
/**
 * Created by AlejandroSerna on 4/6/16.
 */
@RunWith(AndroidJUnit4.class)
public class TestChangeEmail_Alejandro extends AndroidTestCase {
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
    public void testChangeEmail() throws InterruptedException {
        SessionManager session = SessionManager.getInstance(activity.getApplicationContext());
        String oldName = session.getLoggedInName();
        String oldEmail = session.getLoggedInEmail();
        onView(withId(R.id.profile_fab)).perform(click());
        onView(withId(R.id.edit_name)).perform(click()).perform(clearText()).perform(typeText("Bobby"));
        onView(withId(R.id.edit_email)).perform(click()).perform(clearText()).perform(typeText("Bobby@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.edit_name)).check(matches(withText("Bobby")));
        onView(withId(R.id.edit_email)).check(matches(withText("Bobby@gmail.com")));
        onView(withId(R.id.buttonDefaultPositive)).inRoot(isDialog()).perform(click());
        assertFalse(oldName.equals(session.getLoggedInName()));
        assertFalse(oldEmail.equals(session.getLoggedInEmail()));
    }

}
