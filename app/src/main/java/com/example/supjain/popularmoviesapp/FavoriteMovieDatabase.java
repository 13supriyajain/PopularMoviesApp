package com.example.supjain.popularmoviesapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MovieData.class}, version = 1, exportSchema = false)
public abstract class FavoriteMovieDatabase extends RoomDatabase {
    private static volatile FavoriteMovieDatabase INSTANCE;

    static FavoriteMovieDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoriteMovieDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FavoriteMovieDatabase.class, "favorite_movies_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract FavoriteMovieDao favoriteMovieDao();
}
