package com.example.supjain.popularmoviesapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class FavoriteMovieViewModel extends AndroidViewModel {

    private FavoriteMovieRepository mRepository;

    private LiveData<List<MovieData>> mAllFavoriteMovies;

    public FavoriteMovieViewModel(Application application) {
        super(application);
        mRepository = new FavoriteMovieRepository(application);
        mAllFavoriteMovies = mRepository.getFavoriteMovies();
    }

    LiveData<List<MovieData>> getAllFavoriteMovies() {
        return mAllFavoriteMovies;
    }

    public void insertFavoriteMovie(MovieData movieData) {
        mRepository.insertFavoriteMovie(movieData);
    }

    public void deleteFavoriteMovie(MovieData movieData) {
        mRepository.deleteFavoriteMovie(movieData);
    }
}