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
 * This custom Fragment class is for displaying the list of movie videos/trailers.
 */

public class MovieVideosFragment extends Fragment implements MovieVideosAdapter.MovieVideosAdapterOnClickHandler {

    private RecyclerView videosRecyclerView;

    public MovieVideosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.data_recycler_view, container, false);
        videosRecyclerView = rootView.findViewById(R.id.list);
        String movieId = getArguments().getString(SimpleFragmentPagerAdapter.MOVIE_ID);
        fetchAndSetVideoList(movieId);
        return rootView;
    }

    @Override
    public void mClick(String movieVideoUrl) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(movieVideoUrl)));
    }

    private void fetchAndSetVideoList(String movieId) {
        String ApiKeyValue = MovieDataUtil.getApiKeyValue();
        Retrofit retrofit = MovieDataUtil.getRetrofitInstance();
        MovieDataUtil.MovieDataFetchService service = retrofit.create(MovieDataUtil.MovieDataFetchService.class);

        Call<MovieVideosData.TrailersApiResponse> call = service.getMovieVideos(movieId, ApiKeyValue);
        call.enqueue(new Callback<MovieVideosData.TrailersApiResponse>() {
            @Override
            public void onResponse(Call<MovieVideosData.TrailersApiResponse> call, Response<MovieVideosData.TrailersApiResponse> response) {
                setVideosList(response.body().getMovieVideosDataList());
            }

            @Override
            public void onFailure(Call<MovieVideosData.TrailersApiResponse> call, Throwable t) {
            }
        });
    }

    private void setVideosList(List<MovieVideosData> mMovieVideosDataList) {
        MovieVideosAdapter movieVideosAdapter = new MovieVideosAdapter(this);
        movieVideosAdapter.setMovieVideosData(mMovieVideosDataList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        videosRecyclerView.setLayoutManager(linearLayoutManager);
        videosRecyclerView.setAdapter(movieVideosAdapter);
        videosRecyclerView.setHasFixedSize(true);
    }
}
