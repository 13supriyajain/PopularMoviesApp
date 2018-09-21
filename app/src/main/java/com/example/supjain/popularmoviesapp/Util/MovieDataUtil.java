package com.example.supjain.popularmoviesapp.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.example.supjain.popularmoviesapp.BuildConfig;
import com.example.supjain.popularmoviesapp.Data.MovieData;
import com.example.supjain.popularmoviesapp.Data.MovieReviewsData;
import com.example.supjain.popularmoviesapp.Data.MovieVideosData;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This is a helper class containing helper methods to fetch and extract movie data from query url.
 */
public class MovieDataUtil {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String DOMAIN_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY_VALUE = BuildConfig.MOVIE_API_KEY_VALUE;

    private static Retrofit retrofitInstance;

    public static Retrofit getRetrofitInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new retrofit2.Retrofit.Builder()
                    .baseUrl(DOMAIN_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofitInstance;
    }

    public static void loadImageView(Context context, ImageView imageView, String imageUrl) {
        imageUrl = BASE_IMAGE_URL + imageUrl;
        Picasso.with(context).load(imageUrl).into(imageView);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    public static String getApiKeyValue() {
        return API_KEY_VALUE;
    }

    public interface MovieDataFetchService {
        @GET("popular")
        Call<MovieData.MovieApiResponse> getPopularMovies(@Query("api_key") String apiKey);

        @GET("top_rated")
        Call<MovieData.MovieApiResponse> getTopRatedMovies(@Query("api_key") String apiKey);

        @GET("{movie_id}/videos")
        Call<MovieVideosData.TrailersApiResponse> getMovieVideos(@Path("movie_id") String movieId, @Query("api_key") String apiKey);

        @GET("{movie_id}/reviews")
        Call<MovieReviewsData.ReviewsApiResponse> getMovieReviews(@Path("movie_id") String movieId, @Query("api_key") String apiKey);
    }
}
