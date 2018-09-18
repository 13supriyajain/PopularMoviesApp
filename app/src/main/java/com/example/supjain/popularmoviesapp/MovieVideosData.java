package com.example.supjain.popularmoviesapp;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a custom class for holding information about a Movie Videos.
 */

public class MovieVideosData {

    private static final String YOUTUBE_BASE_LINK = "https://www.youtube.com/watch?v=";

    @SerializedName("key")
    private String mVideoId;
    @SerializedName("name")
    private String mVideoName;

    public MovieVideosData(String id, String name) {
        this.mVideoId = id;
        this.mVideoName = name;
    }

    public String getVideoName() {
        return this.mVideoName;
    }

    public String getVideoUrl() {
        return YOUTUBE_BASE_LINK + this.mVideoId;
    }


    public class TrailersApiResponse {

        @SerializedName("results")
        List<MovieVideosData> movieVideosDataList;

        public TrailersApiResponse() {
            this.movieVideosDataList = new ArrayList<>();
        }

        public List<MovieVideosData> getMovieVideosDataList() {
            return this.movieVideosDataList;
        }
    }
}
