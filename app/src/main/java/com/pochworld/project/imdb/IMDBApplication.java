package com.pochworld.project.imdb;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class IMDBApplication extends Application {

    AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("INFO", "Initializing IMDB App");

        // init db
        db =  Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "imdb_database").build();

        // init image loader
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
    }

    //
    public AppDatabase getDatabase() {
        return db;
    }
}
