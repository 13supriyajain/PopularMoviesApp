package com.example.supjain.popularmoviesapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

    public static final int POPULAR_MOVIE_TYPE = 1;
    public static final int TOP_RATED_MOVIE_TYPE = 2;

    private RecyclerView mRecyclerView;
    private MovieDataAdapter mMovieDataAdapter;
    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressbar;
    private List<MovieData> favoriteMovieList;
    private FavoriteMovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        movieViewModel.getAllFavoriteMovies().observe(this, new Observer<List<MovieData>>() {
            @Override
            public void onChanged(@Nullable final List<MovieData> movieDataList) {
                setFavoriteMovieList(movieDataList);
            }
        });

        mRecyclerView = findViewById(R.id.movie_data_recycle_view);
        mErrorMessageDisplay = findViewById(R.id.error_text_view);
        mProgressbar = findViewById(R.id.progressbar);
        mMovieDataAdapter = new MovieDataAdapter(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, MovieDataUtil.calculateNoOfColumns(this));
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mMovieDataAdapter);
        mRecyclerView.setHasFixedSize(true);

        loadMovieData(POPULAR_MOVIE_TYPE);
    }

    private void loadMovieData(int requestType) {
        mProgressbar.setVisibility(View.VISIBLE);
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
            showErrorMessage(getResources().getString(R.string.no_connection_err_msg));
        }
    }

    private boolean hasNetworkConnection() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showMovieData() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressbar.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage(String errorMsg) {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setText(errorMsg);
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

        mMovieDataAdapter.setMovieData(null);
        switch (id) {
            case R.id.sort_by_popular_option:
                loadMovieData(POPULAR_MOVIE_TYPE);
                break;
            case R.id.sort_by_rating_option:
                loadMovieData(TOP_RATED_MOVIE_TYPE);
                break;
            case R.id.sort_by_favorites_option:
                setMovieList(favoriteMovieList);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchMovieListFromServer(int requestType) {
        String ApiKeyValue = MovieDataUtil.getApiKeyValue();
        Retrofit retrofit = MovieDataUtil.getRetrofitInstance();
        MovieDataUtil.MovieDataFetchService service = retrofit.create(MovieDataUtil.MovieDataFetchService.class);
        Call<MovieData.MovieApiResponse> call;

        if (requestType == POPULAR_MOVIE_TYPE)
            call = service.getPopularMovies(ApiKeyValue);
        else
            call = service.getTopRatedMovies(ApiKeyValue);

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
        if (movieDataList != null && movieDataList.size() > 0) {
            showMovieData();
            mMovieDataAdapter.setMovieData(movieDataList);
        } else {
            showErrorMessage(getResources().getString(R.string.no_data_err_msg));
        }
    }

    private void setFavoriteMovieList(List<MovieData> list) {
        favoriteMovieList = list;
    }
}
