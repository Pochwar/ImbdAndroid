package com.pochworld.project.imdb.Accessors;

import android.support.v7.app.AppCompatActivity;

import com.pochworld.project.imdb.Models.Movie;
import com.pochworld.project.imdb.NetworkRequestLauncher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import okhttp3.HttpUrl;
import okhttp3.Response;

public class MovieAccessor extends AppCompatActivity {

    private static String OMDB_ENTRY_POINT = "http://www.omdbapi.com/?apikey=172d5c07";

    public Movie getMovieById(String id) throws ExecutionException, InterruptedException,
            IOException, JSONException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(OMDB_ENTRY_POINT).newBuilder();
        urlBuilder.addQueryParameter("i", id);
        String url = urlBuilder.build().toString();
        NetworkRequestLauncher launcher = new NetworkRequestLauncher();

        Response response = launcher.execute(url).get();

        JSONObject object = (JSONObject) new JSONTokener(response.body().string()).nextValue();

        return parseJsonObject(object);
    }

    public ArrayList<Movie> getMoviesByTitle(String title) throws ExecutionException, InterruptedException,
            IOException, JSONException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(OMDB_ENTRY_POINT).newBuilder();
        urlBuilder.addQueryParameter("s", title);
        String url = urlBuilder.build().toString();
        NetworkRequestLauncher launcher = new NetworkRequestLauncher();

        Response response = launcher.execute(url).get();

        JSONObject object = (JSONObject) new JSONTokener(response.body().string()).nextValue();

        JSONArray array = object.getJSONArray("Search");

        ArrayList movies = new ArrayList<>();
        for (int rank = 0; rank < array.length(); rank++) {
            JSONObject item = array.getJSONObject(rank);
            movies.add(parseJsonObject(item));
        }
        return movies;
    }

    private Movie parseJsonObject(JSONObject item) throws JSONException {
        return new Movie(
                item.has("imdbID") ? item.getString("imdbID") : null,
                item.has("Title") ? item.getString("Title") : null,
                item.has("Poster") ? item.getString("Poster") : null,
                item.has("Year") ? item.getString("Year") : null,
                item.has("Genre") ? item.getString("Genre") : null,
                item.has("Plot") ? item.getString("Plot") : null
        );
    }
}
