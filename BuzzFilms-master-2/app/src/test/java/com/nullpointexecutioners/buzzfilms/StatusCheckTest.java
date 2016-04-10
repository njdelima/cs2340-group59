package com.nullpointexecutioners.buzzfilms;

import android.test.AndroidTestCase;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.nullpointexecutioners.buzzfilms.activities.WelcomeActivity;

import org.junit.Before;
import org.junit.Test;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class StatusCheckTest extends AndroidTestCase {

    public String name;
    public String email;
    public String major;



    @Before
    public void setUp() throws Exception {
        super.setUp();
        BuzzFilms bf = new BuzzFilms();
        bf.onCreate();
        Firebase.setAndroidContext(bf);
    }

    @Test
    public void testJunit() throws Exception {
        WelcomeActivity wa = new WelcomeActivity();
        Firebase mRef = new Firebase("https://buzz-films.firebaseio.com/users");

        assert(wa != null);
        assertEquals(wa.statusCheck("nope!"), false);

        mRef.child("admin").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
                major = dataSnapshot.child("major").getValue(String.class);
                boolean isAdmin = false;
                if (dataSnapshot.child("is_admin").getValue() != null) {
                    isAdmin = dataSnapshot.child("is_admin").getValue(Boolean.class);
                }
                String status;
                if (dataSnapshot.child("status").getValue() != null) {
                    status = dataSnapshot.child("status").getValue(String.class);
                } else {
                    status = "ACTIVE";
                }
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        assertEquals(69, 420);
    }
}