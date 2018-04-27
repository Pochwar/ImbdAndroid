package com.pochworld.project.imdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListFragment extends Fragment {

    private MovieAdapter adapter;
    private EditText search;
    private List<Movie> movies = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*
        parcelisation
         */
        if (savedInstanceState != null && savedInstanceState.containsKey("MOVIES_LIST")) {
            movies = savedInstanceState.getParcelableArrayList("MOVIES_LIST");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View v = inflater.inflate(R.layout.list_layout_fragment, container, false);

        final MovieAccessor movieAccessor = new MovieAccessor();

        search = v.findViewById(R.id.search);

        Button submit = v.findViewById(R.id.submit);

        adapter = new MovieAdapter(this.getContext(),
                R.layout.item_layout, movies);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "search in progress", Toast.LENGTH_SHORT).show();

                ArrayList<Movie> movies = null;
                try {
                    movies = movieAccessor.getMoviesByTitle(search.getText().toString());

                    adapter.getData().clear();

                    adapter.getData().addAll(movies);

                    adapter.notifyDataSetChanged();

                    //hide keyboard after search
                    ((IMDBApplication)ListFragment.this.getActivity().getApplication()).hideKeyboard(getActivity(), v);

                } catch (ExecutionException | InterruptedException | IOException | JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }


            }
        });

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

    /*
    parcelisation
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("MOVIES_LIST", (ArrayList<Movie>) adapter.getData());
    }
}
