package com.example.supjain.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements MovieDataAdapter.MovieAdapterOnClickHandler {

    private static final int POPULAR_MOVIE_TYPE = 1;
    private static final int TOP_RATED_MOVIE_TYPE = 2;

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

        loadMovieData(POPULAR_MOVIE_TYPE);
    }

    private void loadMovieData(int requestType) {
        if (hasNetworkConnection()) {
            switch (requestType) {
                case POPULAR_MOVIE_TYPE:
                    fetchMovieListFromServer(POPULAR_MOVIE_TYPE);
                    break;
                case TOP_RATED_MOVIE_TYPE:
                    fetchMovieListFromServer(TOP_RATED_MOVIE_TYPE);
                    break;
            }
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
        mProgressbar.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        mNetworkErrorMessageDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressbar.setVisibility(View.INVISIBLE);
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

        switch (id) {
            case R.id.sort_by_popular_option:
                mMovieDataAdapter.setMovieData(null);
                loadMovieData(POPULAR_MOVIE_TYPE);
                break;
            case R.id.sort_by_rating_option:
                mMovieDataAdapter.setMovieData(null);
                loadMovieData(TOP_RATED_MOVIE_TYPE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchMovieListFromServer(int requestType) {
        String ApiKeyValue = MovieDataUtil.getApiKeyValue();
        Retrofit retrofit = MovieDataUtil.getRetrofitInstance();
        MovieDataUtil.MovieDataFetchService service = retrofit.create(MovieDataUtil.MovieDataFetchService.class);
        Call<MovieData.MovieApiResponse> call;

        if (requestType == POPULAR_MOVIE_TYPE)
            call = service.getPopularMovies(ApiKeyValue);
        else
            call = service.getTopRatedMovies(ApiKeyValue);

        mProgressbar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<MovieData.MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieData.MovieApiResponse> call, Response<MovieData.MovieApiResponse> response) {
                setMovieList(response.body().getMovieDataList());
            }

            @Override
            public void onFailure(Call<MovieData.MovieApiResponse> call, Throwable t) {
            }
        });
    }

    private void setMovieList(List<MovieData> movieDataList) {
        if (movieDataList != null) {
            showMovieData();
            mMovieDataAdapter.setMovieData(movieDataList);
        } else {
            showErrorMessage();
        }
    }
}
