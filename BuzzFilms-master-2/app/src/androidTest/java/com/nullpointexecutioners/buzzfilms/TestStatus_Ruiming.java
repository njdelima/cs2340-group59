package com.nullpointexecutioners.buzzfilms;

import android.app.Activity;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;

import com.nullpointexecutioners.buzzfilms.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;


@RunWith(AndroidJUnit4.class)
public class TestStatus_Ruiming extends AndroidTestCase {

    @Rule
    public ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);

    Activity activity;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = mMainActivityRule.getActivity();
    }


    @Test
    public void testStatus() {
        try {
            Method m = MainActivity.class.getDeclaredMethod("statusCheck", String.class);
            assertTrue((boolean) m.invoke(activity, "ACTIVE"));
            assertFalse((boolean) m.invoke(activity, "LOCKED"));
            assertFalse((boolean) m.invoke(activity, "BANNED"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @After
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

}