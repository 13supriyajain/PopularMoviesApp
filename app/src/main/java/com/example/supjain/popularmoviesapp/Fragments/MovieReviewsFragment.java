package com.example.supjain.popularmoviesapp.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.supjain.popularmoviesapp.Adapters.MovieReviewsAdapter;
import com.example.supjain.popularmoviesapp.Adapters.SimpleFragmentPagerAdapter;
import com.example.supjain.popularmoviesapp.Data.MovieReviewsData;
import com.example.supjain.popularmoviesapp.R;
import com.example.supjain.popularmoviesapp.Util.MovieDataUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * This custom Fragment class is for displaying the list of movie reviews.
 */

public class MovieReviewsFragment extends Fragment implements MovieReviewsAdapter.MovieReviewsAdapterOnClickHandler {

    private RecyclerView mReviewsRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressbar;

    public MovieReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        mReviewsRecyclerView = rootView.findViewById(R.id.movie_data_recycle_view);
        mErrorMessageDisplay = rootView.findViewById(R.id.error_text_view);
        mProgressbar = rootView.findViewById(R.id.progressbar);

        mProgressbar.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mReviewsRecyclerView.setVisibility(View.INVISIBLE);

        String movieId = getArguments().getString(SimpleFragmentPagerAdapter.MOVIE_ID);
        fetchAndSetReviewList(movieId);

        return rootView;
    }

    @Override
    public void mClick(String movieReviewUrl) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieReviewUrl)));
    }

    private void fetchAndSetReviewList(String movieId) {
        String ApiKeyValue = MovieDataUtil.getApiKeyValue();
        Retrofit retrofit = MovieDataUtil.getRetrofitInstance();
        MovieDataUtil.MovieDataFetchService service = retrofit.create(MovieDataUtil.MovieDataFetchService.class);

        Call<MovieReviewsData.ReviewsApiResponse> call = service.getMovieReviews(movieId, ApiKeyValue);
        call.enqueue(new Callback<MovieReviewsData.ReviewsApiResponse>() {
            @Override
            public void onResponse(Call<MovieReviewsData.ReviewsApiResponse> call, Response<MovieReviewsData.ReviewsApiResponse> response) {
                setReviewList(response.body().getMovieReviewsDataList());
            }

            @Override
            public void onFailure(Call<MovieReviewsData.ReviewsApiResponse> call, Throwable t) {
            }
        });
    }

    private void setReviewList(List<MovieReviewsData> movieReviewsDataList) {
        mProgressbar.setVisibility(View.INVISIBLE);

        if (movieReviewsDataList != null && movieReviewsDataList.size() > 0) {
            mReviewsRecyclerView.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);

            MovieReviewsAdapter movieReviewsAdapter = new MovieReviewsAdapter(this);
            movieReviewsAdapter.setMovieReviewsData(movieReviewsDataList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mReviewsRecyclerView.setLayoutManager(linearLayoutManager);
            mReviewsRecyclerView.setAdapter(movieReviewsAdapter);
            mReviewsRecyclerView.setHasFixedSize(true);
        } else {
            mReviewsRecyclerView.setVisibility(View.INVISIBLE);
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setText(getResources().getString(R.string.no_data_err_msg));
        }
    }
}
