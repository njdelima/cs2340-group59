package ga.neerajdelima.themovieapp;

/**
 * Created by Eduardo on 4/11/2016.
 */

import ga.neerajdelima.themovieapp.EditProfileActivity;
import ga.neerajdelima.themovieapp.model.UserModel;

import android.app.Activity;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.test.AndroidTestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EditProfileTest extends AndroidTestCase {

    @Rule
    public ActivityTestRule<EditProfileActivity> mEditProfileActivity = new ActivityTestRule<>(EditProfileActivity.class);
    Activity activity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = mEditProfileActivity.getActivity();
    }

    @Test
    public void testSaveChanges() throws Exception {
        User currentUser = UserModel.getLoggedInUser();
        onView(withId(R.id.edit_userName)).perform(click()).perform(typeText("eduardo"));
        onView(withId(R.id.edit_password)).perform(click()).perform(typeText("12345"));
        onView(withId(R.id.edit_firstName)).perform(click()).perform(typeText("Eduardo"));
        onView(withId(R.id.edit_lastName)).perform(click()).perform(typeText("Mestanza"), closeSoftKeyboard());
        onView(withId(R.id.edit_userName)).check(matches(withText("eduardo")));
        onView(withId(R.id.edit_password)).check(matches(withText("12345")));
        onView(withId(R.id.edit_firstName)).check(matches(withText("Eduardo")));
        onView(withId(R.id.edit_lastName)).check(matches(withText("Mestanza")));

        onView(withId(R.id.saveChanges)).perform(click());
    }
}
