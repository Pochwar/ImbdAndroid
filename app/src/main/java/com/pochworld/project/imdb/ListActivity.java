package com.pochworld.project.imdb;

import android.os.Bundle;
import android.support.annotation.Nullable;

public class ListActivity extends LayoutActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       setContentView(R.layout.list_layout);
    }
}
