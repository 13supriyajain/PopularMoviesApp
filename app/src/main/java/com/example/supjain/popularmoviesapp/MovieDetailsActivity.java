package com.example.supjain.popularmoviesapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.supjain.popularmoviesapp.Adapters.SimpleFragmentPagerAdapter;
import com.example.supjain.popularmoviesapp.Data.MovieData;
import com.example.supjain.popularmoviesapp.Util.MovieDataUtil;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String MOVIE_OBJ_INTENT_KEY = "MovieDataObj";
    private FavoriteMovieViewModel movieViewModel;
    private List<MovieData> favoriteMovieList;
    private MovieData currentMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        movieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);
        movieViewModel.getAllFavoriteMovies().observe(this, new Observer<List<MovieData>>() {
            @Override
            public void onChanged(@Nullable final List<MovieData> movieDataList) {
                setFavoriteMovieList(movieDataList);
                invalidateOptionsMenu();
            }
        });

        final Bundle intentExtra = getIntent().getExtras();
        if (intentExtra == null) {
            return;
        }
        currentMovieData = intentExtra.getParcelable(MOVIE_OBJ_INTENT_KEY);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorites, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.favorite_icon);
        setFavoriteMenuItemIcon(item);
        return super.onPrepareOptionsMenu(menu);
    }

    private void setFavoriteMenuItemIcon(MenuItem item) {
        if (isFavoriteMovie(currentMovieData))
            item.setIcon(R.drawable.ic_mark_favorite);
        else
            item.setIcon(R.drawable.ic_favorite_border);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.favorite_icon:
                if (isFavoriteMovie(currentMovieData)) {
                    movieViewModel.deleteFavoriteMovie(currentMovieData);
                } else {
                    movieViewModel.insertFavoriteMovie(currentMovieData);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavoriteMovieList(List<MovieData> list) {
        favoriteMovieList = list;
    }

    private boolean isFavoriteMovie(MovieData currentMovieData) {
        boolean isFavorite = false;
        if (favoriteMovieList != null) {
            for (MovieData favMovie : favoriteMovieList) {
                String favMovieId = favMovie.getMovieId();
                String currentMovieId = currentMovieData.getMovieId();
                if (favMovieId.equals(currentMovieId)) {
                    isFavorite = true;
                    break;
                }
            }
        }
        return isFavorite;
    }
}
