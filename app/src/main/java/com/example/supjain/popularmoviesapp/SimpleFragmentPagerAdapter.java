package com.example.supjain.popularmoviesapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * This custom FragmentPagerAdapter class is for returning custom Fragment class objects and page titles
 * based on which page/fragment user is viewing currently.
 */
public class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {

    public static final String MOVIE_SUMMARY_TEXT = "MovieSummaryText";
    public static final String MOVIE_ID = "MovieId";

    // For storing context of the app
    private Context mContext;
    // For referencing current MovieData object
    private MovieData mMovieData;

    // Parametrized constructor of this class to create its object
    public SimpleFragmentPagerAdapter(Context context, FragmentManager fm, MovieData movieData) {
        super(fm);
        mContext = context;
        mMovieData = movieData;
    }

    // Generate custom Fragment class object based on item position
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;

        if (position == 0) {
            fragment = new MovieSummaryFragment();
        } else if (position == 1) {
            fragment = new MovieVideosFragment();
        } else {
            fragment = new MovieReviewsFragment();
        }

        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_SUMMARY_TEXT, mMovieData.getMovieOverview());
        bundle.putString(MOVIE_ID, mMovieData.getMovieId());
        fragment.setArguments(bundle);
        return fragment;
    }

//    @Override
//    public Fragment getItem(int position) {
//        if (position == 0) {
//            Bundle bundle = new Bundle();
//            bundle.putString(MOVIE_SUMMARY_TEXT, mMovieData.getMovieOverview());
//            MovieSummaryFragment fragment = new MovieSummaryFragment();
//            fragment.setArguments(bundle);
//            return fragment;
//        } else if (position == 1) {
//            Bundle bundle = new Bundle();
//            bundle.putString(MOVIE_ID, mMovieData.getMovieId());
//            MovieVideosFragment fragment = new MovieVideosFragment();
//            fragment.setArguments(bundle);
//            return fragment;
//        } else {
//            Bundle bundle = new Bundle();
//            bundle.putString(MOVIE_ID, mMovieData.getMovieId());
//            MovieReviewsFragment fragment = new MovieReviewsFragment();
//            fragment.setArguments(bundle);
//            return fragment;
//        }
//    }

    // Generate title based on item position
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.summary_title);
        } else if (position == 1) {
            return mContext.getString(R.string.trailers_title);
        } else {
            return mContext.getString(R.string.reviews_title);
        }
    }

    // Return count/number of Fragments/Pages present for the app
    @Override
    public int getCount() {
        return 3;
    }
}
