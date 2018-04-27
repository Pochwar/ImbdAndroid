package com.pochworld.project.imdb.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pochworld.project.imdb.AppDatabase;
import com.pochworld.project.imdb.R;

public class DetailsActivity extends LayoutActivity {

    AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.detail_layout);
    }


}




