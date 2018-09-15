package com.example.supjain.popularmoviesapp;

import android.os.Bundle;
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
        MovieData currentMovieData = (MovieData) intentExtra.getParcelable(MOVIE_OBJ_INTENT_KEY);

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

        TextView movieOverviewTextView = findViewById(R.id.movie_overview_tv);
        String overview = currentMovieData.getMovieOverview();
        if (!TextUtils.isEmpty(overview))
            movieOverviewTextView.setText(overview);
    }
}
