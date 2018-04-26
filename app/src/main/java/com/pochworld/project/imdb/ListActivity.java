package com.pochworld.project.imdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_layout);

        final MovieAccessor movieAccessor = new MovieAccessor();

        final EditText search = findViewById(R.id.search);

        Button submit = findViewById(R.id.submit);

        final MovieAdapter adapter = new MovieAdapter(this,
                R.layout.item_layout, new ArrayList<Movie>());

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "search in progress", Toast.LENGTH_SHORT).show();

                ArrayList<Movie> movies = null;
                try {
                    movies = movieAccessor.getMoviesByTitle(search.getText().toString());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.getData().clear();

                adapter.getData().addAll(movies);

                adapter.notifyDataSetChanged();

                hideSoftKeyboard(ListActivity.this, v);
            }
        });


        ListView list = findViewById(R.id.list);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =  new Intent(ListActivity.this, DetailsActivity.class);

                intent.putExtra("MOVIE_ID", adapter.getData().get(position).getId());

                ListActivity.this.startActivity(intent);
            }
        });
    }

    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
