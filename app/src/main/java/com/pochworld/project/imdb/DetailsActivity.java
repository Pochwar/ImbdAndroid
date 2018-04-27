package com.pochworld.project.imdb;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class DetailsActivity extends LayoutActivity {

    AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.detail_layout);
    }


}




