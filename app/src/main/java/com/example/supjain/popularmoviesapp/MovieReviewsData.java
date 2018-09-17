package com.example.supjain.popularmoviesapp;

/**
 * This is a custom class for holding information about a Movie Reviews.
 */

public class MovieReviewsData {

    private String mReviewerName;
    private String mReviewText;
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
}
