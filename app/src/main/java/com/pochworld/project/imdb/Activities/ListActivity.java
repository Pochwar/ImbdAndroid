package com.pochworld.project.imdb.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.pochworld.project.imdb.Activities.LayoutActivity;
import com.pochworld.project.imdb.Fragments.DetailsFragment;
import com.pochworld.project.imdb.R;

public class ListActivity extends LayoutActivity {

    boolean detailFragmentAvailable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_layout);

        detailFragmentAvailable = findViewById(R.id.item_fragment_land) != null;
    }

    public void handleActionByOrientation(String id) {
        if(detailFragmentAvailable) {
            DetailsFragment detailFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.item_fragment_land);

            detailFragment.setContent(id);

        } else {
            // start activity
            Intent intent =  new Intent(this, DetailsActivity.class);

            intent.putExtra("MOVIE_ID", id);

            startActivity(intent);
        }
    }
}
