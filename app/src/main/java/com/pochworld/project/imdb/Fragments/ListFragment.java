package com.pochworld.project.imdb.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pochworld.project.imdb.Activities.DetailsActivity;
import com.pochworld.project.imdb.Activities.ListActivity;
import com.pochworld.project.imdb.IMDBApplication;
import com.pochworld.project.imdb.Models.Movie;
import com.pochworld.project.imdb.Accessors.MovieAccessor;
import com.pochworld.project.imdb.Adapters.MovieAdapter;
import com.pochworld.project.imdb.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

public class ListFragment extends Fragment {

    private MovieAdapter adapter;
    private EditText search;
    private List<Movie> movies = new ArrayList<>();
    private ViewGroup c;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        c = container;

        final View v = inflater.inflate(R.layout.list_layout_fragment, container, false);

        final MovieAccessor movieAccessor = new MovieAccessor();

        search = v.findViewById(R.id.search);

        Button submit = v.findViewById(R.id.submit);

         /*
        parcelisation
         */
        if (savedInstanceState != null && savedInstanceState.containsKey("MOVIES_LIST")) {
            movies = savedInstanceState.getParcelableArrayList("MOVIES_LIST");
        }

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
                ((ListActivity)ListFragment.this.getActivity()).handleActionByOrientation(adapter.getData().get(position).getId());
            }
        });

        return v;
    }

    /*
    parcelisation
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList("MOVIES_LIST", (ArrayList<Movie>) adapter.getData());
    }
}
