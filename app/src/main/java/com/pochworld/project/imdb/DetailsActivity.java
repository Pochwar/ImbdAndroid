package com.pochworld.project.imdb;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DetailsActivity extends LayoutActivity {

    AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        db =  ((IMDBApplication)DetailsActivity.this.getApplication()).getDatabase();

        ImageLoader imgLoader = ImageLoader.getInstance();


        final MovieAccessor movieAccessor = new MovieAccessor();

        setContentView(R.layout.detail_layout);

        TextView title = findViewById(R.id.title);
        TextView id = findViewById(R.id.id);
        ImageView poster = findViewById(R.id.poster);
        TextView genre = findViewById(R.id.genre);
        TextView year = findViewById(R.id.year);
        TextView plot = findViewById(R.id.plot);
        CheckBox favorite = findViewById(R.id.favorite);

        String movieId = getIntent().getStringExtra("MOVIE_ID");

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
            Toast.makeText(getApplicationContext(), "An error occurred", Toast.LENGTH_SHORT).show();
        }


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




