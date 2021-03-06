package com.pochworld.project.imdb.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

@Entity(tableName = "movies")
public class Movie implements Parcelable{

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "poster")
    private String poster;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "genre")
    private String genre;

    @ColumnInfo(name = "plot")
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

    @Ignore
    private Movie(Parcel in) {
        this.title = in.readString();
        this.id = in.readString();
        this.poster = in.readString();
        this.year = in.readString();
        this.genre = in.readString();
        this.plot = in.readString();
    }

    @Ignore
    public int describeContents() {
        return 0;
    }

    @Ignore
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.title);
        out.writeString(this.id);
        out.writeString(this.poster);
        out.writeString(this.year);
        out.writeString(this.genre);
        out.writeString(this.plot);
    }

    @Ignore
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
