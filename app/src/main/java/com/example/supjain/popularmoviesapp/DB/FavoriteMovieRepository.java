package com.example.supjain.popularmoviesapp.DB;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.supjain.popularmoviesapp.Data.MovieData;

import java.util.List;

public class FavoriteMovieRepository {

    private static final int INSERT_TASK = 1;
    private static final int DELETE_TASK = 2;

    private FavoriteMovieDao mFavMovieDao;
    private LiveData<List<MovieData>> mMovieDataList;

    public FavoriteMovieRepository(Application application) {
        FavoriteMovieDatabase db = FavoriteMovieDatabase.getDatabase(application);
        mFavMovieDao = db.favoriteMovieDao();
        mMovieDataList = mFavMovieDao.getAllFavoriteMovies();
    }

    public LiveData<List<MovieData>> getFavoriteMovies() {
        return mMovieDataList;
    }

    public void insertFavoriteMovie(MovieData movieData) {
        new FavoriteMovieAsyncTask(mFavMovieDao, INSERT_TASK).execute(movieData);
    }

    public void deleteFavoriteMovie(MovieData movieData) {
        new FavoriteMovieAsyncTask(mFavMovieDao, DELETE_TASK).execute(movieData);
    }

    private static class FavoriteMovieAsyncTask extends AsyncTask<MovieData, Void, Void> {

        private FavoriteMovieDao mAsyncTaskDao;
        private int taskType;

        FavoriteMovieAsyncTask(FavoriteMovieDao dao, int type) {
            mAsyncTaskDao = dao;
            taskType = type;
        }

        @Override
        protected Void doInBackground(final MovieData... params) {
            switch (taskType) {
                case INSERT_TASK:
                    mAsyncTaskDao.insertFavoriteMovie(params[0]);
                    break;
                case DELETE_TASK:
                    mAsyncTaskDao.deleteFavoriteMovie(params[0]);
                    break;
            }
            return null;
        }
    }
}
