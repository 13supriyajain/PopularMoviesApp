package com.example.supjain.popularmoviesapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a custom class for holding information about a Movie.
 */
@Entity(tableName = "favorite_movies")
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

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String mMovieId;

    @ColumnInfo(name = "title")
    @SerializedName("original_title")
    private String mMovieTitle;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String mMoviePosterUrl;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String mMovieReleaseDate;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private String mMovieRating;

    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    private String mMovieVoteCount;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String mMovieOverview;

    public MovieData() {
    }

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

    public void setMovieId(@NonNull String movieId) {
        this.mMovieId = movieId;
    }

    public String getMovieTitle() {
        return mMovieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.mMovieTitle = movieTitle;
    }

    public String getMoviePosterUrl() {
        return mMoviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.mMoviePosterUrl = moviePosterUrl;
    }

    public String getMovieReleaseDate() {
        return mMovieReleaseDate;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.mMovieReleaseDate = movieReleaseDate;
    }

    public String getMovieRating() {
        return mMovieRating;
    }

    public void setMovieRating(String movieRating) {
        this.mMovieRating = movieRating;
    }

    public String getMovieVoteCount() {
        return mMovieVoteCount;
    }

    public void setMovieVoteCount(String movieVoteCount) {
        this.mMovieVoteCount = movieVoteCount;
    }

    public String getMovieOverview() {
        return mMovieOverview;
    }

    public void setMovieOverview(String movieOverview) {
        this.mMovieOverview = movieOverview;
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
