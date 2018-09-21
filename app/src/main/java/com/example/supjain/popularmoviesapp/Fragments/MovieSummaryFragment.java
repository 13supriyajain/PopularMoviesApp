package com.example.supjain.popularmoviesapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.supjain.popularmoviesapp.Adapters.SimpleFragmentPagerAdapter;
import com.example.supjain.popularmoviesapp.R;

/**
 * This custom Fragment class is for displaying movie_summary_text.xml layout file content.
 */
public class MovieSummaryFragment extends Fragment {

    public MovieSummaryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_summary_text, container, false);

        String movieSummary = getArguments().getString(SimpleFragmentPagerAdapter.MOVIE_SUMMARY_TEXT);
        TextView summaryTextView = rootView.findViewById(R.id.movie_overview_tv);
        summaryTextView.setText(movieSummary);

        return rootView;
    }
}
