package com.example.supjain.popularmoviesapp;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a helper class containing helper methods to fetch and extract movie data from query url.
 */
public class MovieDataUtil {

    public static final String LOG_TAG = MovieDataUtil.class.getName();
    public static final int POPULAR_MOVIE_SEARCH_KEY = 1;
    public static final int TOP_RATED_MOVIE_SEARCH_KEY = 2;
    private static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String DOMAIN_URL = "https://api.themoviedb.org/3/movie";
    private static final String POPULAR_MOVIE_SEARCH = "/popular";
    private static final String TOP_RATED_MOVIE_SEARCH = "/top_rated";
    private static final String API_KEY_KEYWORD = "?api_key=";
    private static final String API_KEY_VALUE = BuildConfig.MOVIE_API_KEY_VALUE;
    private static final String API_KEY_QUAERY_PARAM = API_KEY_KEYWORD + API_KEY_VALUE;

    private static final String VIDEOS_QUERY = "/videos";
    private static final String REVIEWS_QUERY = "/reviews";

    /**
     * Query the movie DBt and return a list of {@link MovieData} objects.
     */
    public static List<MovieData> fetchMovieData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link MovieData}
        List<MovieData> movieDataList = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link MovieData}
        return movieDataList;
    }

    /**
     * Return a list of {@link MovieData} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<MovieData> extractFeatureFromJson(String movieJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding movie data to
        List<MovieData> movieDataList = new ArrayList<>();

        /** Try to parse the JSON response string. If there's a problem with the way the JSON
         * is formatted, a JSONException exception object will be thrown.
         * Catch the exception so the app doesn't crash, and print the error message to the logs.
         */
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(movieJSON);

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of movie data.
            JSONArray movieDataArray = baseJsonResponse.getJSONArray("results");

            // For each movie data object in the movieDataArray, create an {@link MovieData} object
            for (int i = 0; i < movieDataArray.length(); i++) {

                // Get a single movie data object at position i within the list of movie data objects
                JSONObject currentMovieData = movieDataArray.getJSONObject(i);

                // Extract the value for the key called "id"
                String id = currentMovieData.getString("id");

                // Extract the value for the key called "original_title"
                String title = currentMovieData.getString("original_title");

                // Extract the value for the key called "poster_path"
                String url = currentMovieData.getString("poster_path");

                // Extract the value for the key called "release_date"
                String date = currentMovieData.getString("release_date");

                // Extract the value for the key called "vote_count"
                String vote_count = currentMovieData.getString("vote_count");

                // Extract the value for the key called "vote_average"
                String rating = currentMovieData.getString("vote_average");

                // Extract the value for the key called "overview"
                String overview = currentMovieData.getString("overview");

                // Create a new {@link MovieData} object with the values extracted above
                MovieData movieData = new MovieData(id, title, url, date, rating, vote_count, overview);

                // Add the new {@link MovieData} to the movieDataList.
                movieDataList.add(movieData);
            }

        } catch (JSONException e) {
            /** If an error is thrown when executing any of the above statements in the "try" block,
             * catch the exception here, so the app doesn't crash. Print a log message
             * with the message from the exception.
             */
            Log.e(LOG_TAG, "Problem parsing the movie data JSON results" + movieJSON, e);
        }

        // Return the list of movie data
        return movieDataList;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
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

    public static String getRequestUrl(int requestType) {
        String url = "";
        if (isValidRequestType(requestType)) {
            url += DOMAIN_URL;
            switch (requestType) {
                case POPULAR_MOVIE_SEARCH_KEY:
                    url += POPULAR_MOVIE_SEARCH;
                    break;
                case TOP_RATED_MOVIE_SEARCH_KEY:
                    url += TOP_RATED_MOVIE_SEARCH;
                    break;
            }
            url += API_KEY_QUAERY_PARAM;
        }
        return url;
    }

    private static boolean isValidRequestType(int requestType) {
        return (requestType == POPULAR_MOVIE_SEARCH_KEY) ||
                (requestType == TOP_RATED_MOVIE_SEARCH_KEY);
    }
}
