package com.pochworld.project.imdb;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteActivity extends LayoutActivity {

    private MovieAdapter adapter;

    AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        db =  Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "imdb_database").build();

        setContentView(R.layout.favorites_layout);

        List<Movie> movies = null;
        try {
            movies = new GetFavorites().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        adapter = new MovieAdapter(this, R.layout.item_layout, movies);

        ListView list = findViewById(R.id.list);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =  new Intent(FavoriteActivity.this, DetailsActivity.class);

                intent.putExtra("MOVIE_ID", adapter.getData().get(position).getId());

                FavoriteActivity.this.startActivity(intent);
            }
        });
    }

    public class GetFavorites extends AsyncTask<Void, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(Void... params) {
            return db.movieDao().getAll();
        }
    }
}
