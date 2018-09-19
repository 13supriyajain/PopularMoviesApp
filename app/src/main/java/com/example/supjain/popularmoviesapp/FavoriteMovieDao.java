package com.example.supjain.popularmoviesapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movies")
    public LiveData<List<MovieData>> getAllFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFavoriteMovie(MovieData movieData);

    @Delete
    public void deleteFavoriteMovie(MovieData movieData);
}
