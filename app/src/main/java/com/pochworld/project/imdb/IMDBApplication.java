package com.pochworld.project.imdb;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

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

    public Void hideKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);

        return null;
    }
}
