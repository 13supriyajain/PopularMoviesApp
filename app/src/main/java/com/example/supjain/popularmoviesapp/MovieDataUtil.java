package com.example.supjain.popularmoviesapp;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This is a helper class containing helper methods to fetch and extract movie data from query url.
 */
public class MovieDataUtil {

    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String VIDEOS_QUERY = "/videos";
    private static final String REVIEWS_QUERY = "/reviews";

    private static Retrofit retrofitInstance;

    public static Retrofit getRetrofitInstance(String url) {
        if (retrofitInstance == null) {
            retrofitInstance = new retrofit2.Retrofit.Builder()
                    .baseUrl(url)
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

    public interface MovieDataFetchService {
        @GET("popular")
        Call<MovieData.MovieApiResponse> getPopularMovies(@Query("api_key") String apiKey);

        @GET("top_rated")
        Call<MovieData.MovieApiResponse> getTopRatedMovies(@Query("api_key") String apiKey);
    }
}
