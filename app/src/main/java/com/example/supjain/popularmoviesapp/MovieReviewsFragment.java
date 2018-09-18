package com.example.supjain.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * This custom Fragment class is for displaying the list of movie reviews.
 */

public class MovieReviewsFragment extends Fragment implements MovieReviewsAdapter.MovieReviewsAdapterOnClickHandler {

    private RecyclerView reviewsListView;

    public MovieReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.data_recycler_view, container, false);
        reviewsListView = rootView.findViewById(R.id.list);

        String movieId = getArguments().getString(SimpleFragmentPagerAdapter.MOVIE_ID);
        // make rest api call to get list of videos for the movie
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

    private void setReviewList(List<MovieReviewsData> mMovieReviewsDataList) {
        MovieReviewsAdapter movieReviewsAdapter = new MovieReviewsAdapter(this);
        movieReviewsAdapter.setMovieReviewsData(mMovieReviewsDataList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        reviewsListView.setLayoutManager(linearLayoutManager);
        reviewsListView.setAdapter(movieReviewsAdapter);
        reviewsListView.setHasFixedSize(true);
    }
}
