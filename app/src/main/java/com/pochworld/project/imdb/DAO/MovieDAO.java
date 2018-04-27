package com.pochworld.project.imdb.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pochworld.project.imdb.Models.Movie;

import java.util.List;

@Dao
public interface MovieDAO {
    @Query("SELECT * FROM movies")
    List<Movie> getAll();

    @Query("SELECT * FROM movies WHERE title LIKE :title")
    List<Movie> getMoviesByTitle(String title);

    @Query("SELECT * FROM movies WHERE id =:id")
    Movie getMovieById(String id);

    @Insert
    void insertAll(Movie... movies);

    @Delete
    void delete(Movie movie);
}