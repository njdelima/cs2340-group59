package com.nullpointexecutioners.buzzfilms;

import com.firebase.client.Firebase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Extending the Application class allows us to set Firebase's context (this app)
 */
public class BuzzFilms extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        final Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        final Picasso built = builder.build();
        // Red indicator = network, blue = disk, green = memory
        //built.setIndicatorsEnabled(true);
        //built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
