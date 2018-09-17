package com.example.supjain.popularmoviesapp;

/**
 * This is a custom class for holding information about a Movie Videos.
 */

public class MovieVideosData {

    private static final String YOUTUBE_BASE_LINK = "https://www.youtube.com/watch?v=";

    private String mVideoId;
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
}
