package com.pochworld.project.imdb.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.pochworld.project.imdb.Activities.DetailsActivity;
import com.pochworld.project.imdb.AppDatabase;
import com.pochworld.project.imdb.IMDBApplication;
import com.pochworld.project.imdb.Models.Movie;
import com.pochworld.project.imdb.Adapters.MovieAdapter;
import com.pochworld.project.imdb.R;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class FavoriteFragment extends Fragment {

    private MovieAdapter adapter;

    AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        db =  ((IMDBApplication)FavoriteFragment.this.getActivity().getApplication()).getDatabase();


        View v = inflater.inflate(R.layout.favorites_layout_fragment, container, false);

        List<Movie> movies = null;
        try {
            movies = new GetFavorites().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        adapter = new MovieAdapter(getContext(), R.layout.item_layout, movies);

        ListView list = v.findViewById(R.id.list);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =  new Intent(getContext(), DetailsActivity.class);

                intent.putExtra("MOVIE_ID", adapter.getData().get(position).getId());

                startActivity(intent);
            }
        });

        return v;
    }

    public class GetFavorites extends AsyncTask<Void, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(Void... params) {
            return db.movieDao().getAll();
        }
    }
}
