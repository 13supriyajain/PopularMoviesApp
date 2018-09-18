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

import java.util.ArrayList;
import java.util.List;

/**
 * This custom Fragment class is for displaying the list of movie reviews.
 */

public class MovieReviewsFragment extends Fragment implements MovieReviewsAdapter.MovieReviewsAdapterOnClickHandler {

    public MovieReviewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.data_recycler_view, container, false);

        String movieId = getArguments().getString(SimpleFragmentPagerAdapter.MOVIE_ID);
        // make rest api call to get list of videos for the movie
        List<MovieReviewsData> mMovieReviewsDataList = new ArrayList<>();
        mMovieReviewsDataList.add(new MovieReviewsData("garethmb", "The Mercenary with a mouth is back with the eagerly awaited arrival of Deadpool.",
                "https://www.themoviedb.org/review/5afa5a93925141414b005cf0"));
        mMovieReviewsDataList.add(new MovieReviewsData("TobyBenson", "An utterly hilarious movie, with hundreds of pop culture references",
                "https://www.themoviedb.org/review/5b15d58fc3a368534f00fde5"));

        MovieReviewsAdapter movieReviewsAdapter = new MovieReviewsAdapter(this);
        movieReviewsAdapter.setMovieReviewsData(mMovieReviewsDataList);

        RecyclerView reviewsListView = rootView.findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        reviewsListView.setLayoutManager(linearLayoutManager);
        reviewsListView.setAdapter(movieReviewsAdapter);
        reviewsListView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void mClick(String movieReviewUrl) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieReviewUrl)));
    }
}
