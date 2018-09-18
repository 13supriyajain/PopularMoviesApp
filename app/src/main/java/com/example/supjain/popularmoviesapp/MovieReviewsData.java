package com.example.supjain.popularmoviesapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a custom class for holding information about a Movie Reviews.
 */

public class MovieReviewsData {

    @SerializedName("author")
    private String mReviewerName;
    @SerializedName("content")
    private String mReviewText;
    @SerializedName("url")
    private String mReviewUrl;

    public MovieReviewsData(String reviewerName, String reviewerText, String url) {
        this.mReviewerName = reviewerName;
        this.mReviewText = reviewerText;
        this.mReviewUrl = url;
    }

    public String getReviewerName() {
        return this.mReviewerName;
    }

    public String getReviewText() {
        return this.mReviewText;
    }

    public String getReviewUrl() {
        return this.mReviewUrl;
    }


    public class ReviewsApiResponse {

        @SerializedName("results")
        List<MovieReviewsData> movieReviewsDataList;

        public ReviewsApiResponse() {
            this.movieReviewsDataList = new ArrayList<>();
        }

        public List<MovieReviewsData> getMovieReviewsDataList() {
            return this.movieReviewsDataList;
        }
    }
}
