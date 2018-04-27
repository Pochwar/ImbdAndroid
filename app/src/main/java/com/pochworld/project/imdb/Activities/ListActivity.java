package com.pochworld.project.imdb.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.pochworld.project.imdb.Activities.LayoutActivity;
import com.pochworld.project.imdb.R;

public class ListActivity extends LayoutActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       setContentView(R.layout.list_layout);
    }
}
