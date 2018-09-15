package com.example.supjain.popularmoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This is a custom class for holding information about a Movie.
 */
public class MovieData implements Parcelable {

    public static final Creator<MovieData> CREATOR = new Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel in) {
            return new MovieData(in);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
    private String mMovieTitle;
    private String mMoviePosterUrl;
    private String mMovieReleaseDate;
    private String mMovieRating;
    private String mMovieVoteCount;
    private String mMovieOverview;

    // Parametrized constructor to create object with specific movie information
    public MovieData(String title, String url, String date, String rating, String voteCount, String overview) {
        this.mMovieTitle = title;
        this.mMoviePosterUrl = url;
        this.mMovieReleaseDate = date;
        this.mMovieRating = rating;
        this.mMovieVoteCount = voteCount;
        this.mMovieOverview = overview;
    }

    protected MovieData(Parcel in) {
        mMovieTitle = in.readString();
        mMoviePosterUrl = in.readString();
        mMovieReleaseDate = in.readString();
        mMovieRating = in.readString();
        mMovieVoteCount = in.readString();
        mMovieOverview = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMovieTitle);
        dest.writeString(mMoviePosterUrl);
        dest.writeString(mMovieReleaseDate);
        dest.writeString(mMovieRating);
        dest.writeString(mMovieVoteCount);
        dest.writeString(mMovieOverview);
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public String getMoviePosterUrl() {
        return mMoviePosterUrl;
    }

    public String getMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    public String getMovieRating() {
        return mMovieRating;
    }

    public String getMovieVoteCount() {
        return mMovieVoteCount;
    }

    public String getMovieOverview() {
        return mMovieOverview;
    }
}
