package com.pochworld.project.imdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movies;

    private Context context;

    private ImageLoader imgLoader;

    public MovieAdapter(@NonNull Context context, int resource, List<Movie> movies) {
        super(context, resource, movies);

        this.context = context;
        this.movies = movies;

        imgLoader = ImageLoader.getInstance();
    }

    public List<Movie> getData() {
        return this.movies;
    }

    static class ViewHolder {
        public TextView title;
        public ImageView poster;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = convertView;

        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_layout, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = rowView.findViewById(R.id.title);
            viewHolder.poster = rowView.findViewById(R.id.poster);
            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.title.setText(movies.get(position).getTitle());
        imgLoader.displayImage(movies.get(position).getPoster(), holder.poster);

        return rowView;
    }
}
