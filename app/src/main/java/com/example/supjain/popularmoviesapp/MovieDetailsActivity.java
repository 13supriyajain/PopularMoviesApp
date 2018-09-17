package com.example.supjain.popularmoviesapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_OBJ_INTENT_KEY = "MovieDataObj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        final Bundle intentExtra = getIntent().getExtras();
        if (intentExtra == null) {
            return;
        }
        MovieData currentMovieData = intentExtra.getParcelable(MOVIE_OBJ_INTENT_KEY);

        String movieTitle = currentMovieData.getMovieTitle();
        if (!TextUtils.isEmpty(movieTitle))
            setTitle(movieTitle);

        ImageView moviePosterImageView = findViewById(R.id.movie_poster_image_view);
        String imageUrl = currentMovieData.getMoviePosterUrl();
        if (!TextUtils.isEmpty(imageUrl))
            MovieDataUtil.loadImageView(this, moviePosterImageView, imageUrl);

        TextView releaseDateTextView = findViewById(R.id.movie_release_date_tv);
        String releaseDate = currentMovieData.getMovieReleaseDate();
        if (!TextUtils.isEmpty(releaseDate))
            releaseDateTextView.setText(releaseDate);

        TextView ratingsTextView = findViewById(R.id.movie_rating_tv);
        String ratings = currentMovieData.getMovieRating();
        if (!TextUtils.isEmpty(ratings))
            ratingsTextView.setText(ratings);

        TextView voteCountTextView = findViewById(R.id.movie_vote_count_tv);
        String voteCount = currentMovieData.getMovieVoteCount();
        if (!TextUtils.isEmpty(voteCount))
            voteCountTextView.setText(voteCount);

        // Find the view pager that will allow the user to swipe between fragments
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        SimpleFragmentPagerAdapter adapter = new SimpleFragmentPagerAdapter(this, getSupportFragmentManager(), currentMovieData);

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Connect the tab layout with the view pager. This will
        //   1. Update the tab layout when the view pager is swiped
        //   2. Update the view pager when a tab is selected
        //   3. Set the tab layout's tab names with the view pager's adapter's titles by calling onPageTitle()
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
