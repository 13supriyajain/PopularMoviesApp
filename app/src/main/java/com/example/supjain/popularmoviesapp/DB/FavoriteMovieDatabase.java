package com.example.supjain.popularmoviesapp.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.supjain.popularmoviesapp.Data.MovieData;

@Database(entities = {MovieData.class}, version = 1, exportSchema = false)
public abstract class FavoriteMovieDatabase extends RoomDatabase {
    private static final String DB_NAME = "favorite_movies_db";
    private static volatile FavoriteMovieDatabase INSTANCE;

    static FavoriteMovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteMovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteMovieDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FavoriteMovieDao favoriteMovieDao();
}
