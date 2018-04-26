package com.pochworld.project.imdb;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{

    private String title;
    private String id;
    private String poster;
    private String year;
    private String genre;
    private String plot;

    /*
    Parcelisation
     */
    public Movie(String id, String title, String poster, String year, String genre, String plot) {
        this.setTitle(title);
        this.setId(id);
        this.setPoster(poster);
        this.setYear(year);
        this.setGenre(genre);
        this.setPlot(plot);
    }

    private Movie(Parcel in) {
        this.title = in.readString();
        this.id = in.readString();
        this.poster = in.readString();
        this.year = in.readString();
        this.genre = in.readString();
        this.plot = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.title);
        out.writeString(this.id);
        out.writeString(this.poster);
        out.writeString(this.year);
        out.writeString(this.genre);
        out.writeString(this.plot);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    /*
    End parcelisation
     */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }
}
