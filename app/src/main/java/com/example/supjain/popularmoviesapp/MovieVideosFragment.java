package com.example.supjain.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * This custom Fragment class is for displaying the list of movie videos/trailers.
 */

public class MovieVideosFragment extends Fragment implements MovieVideosAdapter.MovieVideosAdapterOnClickHandler {

    private static final String SHARE_INTENT_TYPE = "text/plain";
    private RecyclerView mVideosRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mProgressbar;

    public MovieVideosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);
        mVideosRecyclerView = rootView.findViewById(R.id.movie_data_recycle_view);
        mErrorMessageDisplay = rootView.findViewById(R.id.error_text_view);
        mProgressbar = rootView.findViewById(R.id.progressbar);
        mProgressbar.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mVideosRecyclerView.setVisibility(View.INVISIBLE);

        String movieId = getArguments().getString(SimpleFragmentPagerAdapter.MOVIE_ID);
        fetchAndSetVideoList(movieId);
        return rootView;
    }

    @Override
    public void mClick(int viewId, String movieVideoUrl, String movieVideoTitle) {
        switch (viewId) {
            case R.id.video_share_btn:
                shareVideoUrl(movieVideoUrl, movieVideoTitle);
                break;
            case R.id.video_container_layout:
            default:
                openVideo(movieVideoUrl);
                break;
        }
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

    private void setVideosList(List<MovieVideosData> movieVideosDataList) {
        mProgressbar.setVisibility(View.INVISIBLE);

        if (movieVideosDataList != null && movieVideosDataList.size() > 0) {
            mVideosRecyclerView.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);
            MovieVideosAdapter movieVideosAdapter = new MovieVideosAdapter(this);
            movieVideosAdapter.setMovieVideosData(movieVideosDataList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            mVideosRecyclerView.setLayoutManager(linearLayoutManager);
            mVideosRecyclerView.setAdapter(movieVideosAdapter);
            mVideosRecyclerView.setHasFixedSize(true);
        } else {
            mVideosRecyclerView.setVisibility(View.INVISIBLE);
            mErrorMessageDisplay.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setText(getResources().getString(R.string.no_data_err_msg));
        }
    }

    private void shareVideoUrl(String videoUrl, String videoTitle) {
        if (!TextUtils.isEmpty(videoUrl) && !TextUtils.isEmpty(videoTitle)) {
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType(SHARE_INTENT_TYPE);

            /* This flag clears the called app from the activity stack,
            so users arrive in the expected place next time this application is restarted.*/
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, videoTitle);
            share.putExtra(Intent.EXTRA_TEXT, videoUrl);

            startActivity(Intent.createChooser(share, getString(R.string.intent_chooser_title)));
        }
    }

    private void openVideo(String movieVideoUrl) {
        if (!TextUtils.isEmpty(movieVideoUrl)) {
            Uri uri = Uri.parse(movieVideoUrl);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
