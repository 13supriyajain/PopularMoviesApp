package com.example.supjain.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieDataAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private MovieDataAdapter mMovieDataAdapter;
    private TextView mNetworkErrorMessageDisplay;
    private ProgressBar mProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.movie_posters_recycle_view);
        mNetworkErrorMessageDisplay = findViewById(R.id.no_connection_text_view);
        mProgressbar = findViewById(R.id.progressbar);
        mMovieDataAdapter = new MovieDataAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, MovieDataUtil.calculateNoOfColumns(this));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mMovieDataAdapter);
        mRecyclerView.setHasFixedSize(true);

        String requestUrl = MovieDataUtil.getRequestUrl(MovieDataUtil.POPULAR_MOVIE_SEARCH_KEY);
        loadMovieData(requestUrl);
    }

    private void loadMovieData(String requestUrl) {
        if (hasNetworkConnection()) {
            showMovieData();
            new FetchMovieDataTask().execute(requestUrl);
        } else {
            showErrorMessage();
        }
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showMovieData() {
        mNetworkErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mNetworkErrorMessageDisplay.setVisibility(View.VISIBLE);
        mProgressbar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void mClick(MovieData movieData) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_OBJ_INTENT_KEY, movieData);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String url = "";

        switch (id) {
            case R.id.sort_by_popular_option:
                url = MovieDataUtil.getRequestUrl(MovieDataUtil.POPULAR_MOVIE_SEARCH_KEY);
                break;
            case R.id.sort_by_rating_option:
                url = MovieDataUtil.getRequestUrl(MovieDataUtil.TOP_RATED_MOVIE_SEARCH_KEY);
                break;
        }

        mMovieDataAdapter.setMovieData(null);
        loadMovieData(url);
        return super.onOptionsItemSelected(item);
    }

    public class FetchMovieDataTask extends AsyncTask<String, Void, List<MovieData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MovieData> doInBackground(String... params) {
            List<MovieData> movieDataList = null;

            if (params.length != 0) {
                String url = params[0];
                movieDataList = MovieDataUtil.fetchMovieData(url);
            }
            return movieDataList;
        }

        @Override
        protected void onPostExecute(List<MovieData> movieDataList) {
            mProgressbar.setVisibility(View.INVISIBLE);
            if (movieDataList != null) {
                showMovieData();
                mMovieDataAdapter.setMovieData(movieDataList);
            } else {
                showErrorMessage();
            }
        }
    }
}
