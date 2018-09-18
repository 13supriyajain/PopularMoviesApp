package com.example.supjain.popularmoviesapp;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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

    @SerializedName("id")
    private String mMovieId;
    @SerializedName("original_title")
    private String mMovieTitle;
    @SerializedName("poster_path")
    private String mMoviePosterUrl;
    @SerializedName("release_date")
    private String mMovieReleaseDate;
    @SerializedName("vote_average")
    private String mMovieRating;
    @SerializedName("vote_count")
    private String mMovieVoteCount;
    @SerializedName("overview")
    private String mMovieOverview;

    // Parametrized constructor to create object with specific movie information
    public MovieData(String id, String title, String url, String date, String rating, String voteCount, String overview) {
        this.mMovieId = id;
        this.mMovieTitle = title;
        this.mMoviePosterUrl = url;
        this.mMovieReleaseDate = date;
        this.mMovieRating = rating;
        this.mMovieVoteCount = voteCount;
        this.mMovieOverview = overview;
    }

    protected MovieData(Parcel in) {
        mMovieId = in.readString();
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
        dest.writeString(mMovieId);
        dest.writeString(mMovieTitle);
        dest.writeString(mMoviePosterUrl);
        dest.writeString(mMovieReleaseDate);
        dest.writeString(mMovieRating);
        dest.writeString(mMovieVoteCount);
        dest.writeString(mMovieOverview);
    }

    public String getMovieId() {
        return mMovieId;
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


    public class MovieApiResponse {

        @SerializedName("results")
        List<MovieData> movieDataList;

        public MovieApiResponse() {
            this.movieDataList = new ArrayList<>();
        }

        public List<MovieData> getMovieDataList() {
            return this.movieDataList;
        }
    }
}
