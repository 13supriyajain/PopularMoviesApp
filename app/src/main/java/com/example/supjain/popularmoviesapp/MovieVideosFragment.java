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
 * This custom Fragment class is for displaying the list of movie videos/trailers.
 */

public class MovieVideosFragment extends Fragment implements MovieVideosAdapter.MovieVideosAdapterOnClickHandler {

    public MovieVideosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.data_recycler_view, container, false);

        String movieId = getArguments().getString(SimpleFragmentPagerAdapter.MOVIE_ID);
        // make rest api call to get list of videos for the movie
        List<MovieVideosData> mMovieVideosDataList = new ArrayList<>();
        mMovieVideosDataList.add(new MovieVideosData("Z5ezsReZcxU", "No Good Deed"));
        mMovieVideosDataList.add(new MovieVideosData("2-5Wv9UGkN8", "Deadpool 2 | Official HD  Deadpool's \\\"Wet on Wet\\\" Teaser | 2018"));

        MovieVideosAdapter movieVideosAdapter = new MovieVideosAdapter(this, mMovieVideosDataList);
        RecyclerView videosListView = rootView.findViewById(R.id.list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        videosListView.setLayoutManager(linearLayoutManager);
        videosListView.setAdapter(movieVideosAdapter);
        videosListView.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void mClick(String movieVideoUrl) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieVideoUrl)));
    }
}
