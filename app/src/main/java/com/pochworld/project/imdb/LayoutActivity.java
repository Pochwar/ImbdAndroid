package com.pochworld.project.imdb;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public abstract class LayoutActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent_home =  new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent_home);

                return true;

            case R.id.menu_favorite:
                Intent intent_fav =  new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(intent_fav);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
