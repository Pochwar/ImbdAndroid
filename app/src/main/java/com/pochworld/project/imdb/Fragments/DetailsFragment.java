package com.pochworld.project.imdb.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.pochworld.project.imdb.AppDatabase;
import com.pochworld.project.imdb.IMDBApplication;
import com.pochworld.project.imdb.Models.Movie;
import com.pochworld.project.imdb.Accessors.MovieAccessor;
import com.pochworld.project.imdb.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DetailsFragment extends Fragment {

    AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        db =  ((IMDBApplication)DetailsFragment.this.getActivity().getApplication()).getDatabase();

        ImageLoader imgLoader = ImageLoader.getInstance();

        final MovieAccessor movieAccessor = new MovieAccessor();

        View v = inflater.inflate(R.layout.detail_layout_fragment, container, false);

        TextView title = v.findViewById(R.id.title);
        TextView id = v.findViewById(R.id.id);
        ImageView poster = v.findViewById(R.id.poster);
        TextView genre = v.findViewById(R.id.genre);
        TextView year = v.findViewById(R.id.year);
        TextView plot = v.findViewById(R.id.plot);
        CheckBox favorite = v.findViewById(R.id.favorite);

        String movieId = this.getActivity().getIntent().getStringExtra("MOVIE_ID");

        // check if movie is in favorite
        try {
            favorite.setChecked(new CheckMovieIsInDB().execute(movieId).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            final Movie movie = movieAccessor.getMovieById(movieId);

            title.setText(movie.getTitle());
            id.setText(movie.getId());
            imgLoader.displayImage(movie.getPoster(), poster);
            genre.setText(movie.getGenre());
            year.setText(movie.getYear());
            plot.setText(movie.getPlot());

            // add listener on favorite checkbox
            favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
            {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                {
                    if ( isChecked )
                    {
                        // add to database
                        try {
                            new AddToFavorite().execute(movie).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // remove from database
                        try {
                            new RemoveFromFavorite().execute(movie).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });

        } catch (ExecutionException | InterruptedException | JSONException | IOException e) {
            e.printStackTrace();
            Toast.makeText(this.getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    public class CheckMovieIsInDB extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... movieId) {
            Boolean movieInFavorite;

            movieInFavorite = db.movieDao().getMovieById(movieId[0]) != null;

            return movieInFavorite;
        }
    }

    public class AddToFavorite extends AsyncTask<Movie, Void, Void> {

        @Override
        protected Void doInBackground(Movie... movie) {
            db.movieDao().insertAll(movie);
            return null;
        }
    }

    public class RemoveFromFavorite extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movie) {
            db.movieDao().delete(movie[0]);
            return null;
        }
    }
}
