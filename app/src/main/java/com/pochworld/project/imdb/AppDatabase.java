package com.pochworld.project.imdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.pochworld.project.imdb.DAO.MovieDAO;
import com.pochworld.project.imdb.Models.Movie;


@Database(entities = {Movie.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDAO movieDao();
}