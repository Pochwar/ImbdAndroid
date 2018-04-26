package com.pochworld.project.imdb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class DetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ImageLoader imgLoader = ImageLoader.getInstance();


        final MovieAccessor movieAccessor = new MovieAccessor();

        setContentView(R.layout.detail_layout);

        TextView title = findViewById(R.id.title);
        TextView id = findViewById(R.id.id);
        ImageView poster = findViewById(R.id.poster);
        TextView genre = findViewById(R.id.genre);
        TextView year = findViewById(R.id.year);
        TextView plot = findViewById(R.id.plot);

        String movieId = getIntent().getStringExtra("MOVIE_ID");

        Movie movie = null;
        try {
            movie = movieAccessor.getMovieById(movieId);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        title.setText(movie.getTitle());
        id.setText(movie.getId());
        imgLoader.displayImage(movie.getPoster(), poster);
        genre.setText(movie.getGenre());
        year.setText(movie.getYear());
        plot.setText(movie.getPlot());
    }
}
