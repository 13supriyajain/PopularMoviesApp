package com.example.supjain.popularmoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * This is a custom Adapter class to set and display movie data.
 */
public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.MovieDataAdapterViewHolder> {

    private List<MovieData> mMovieDataList;
    private MovieAdapterOnClickHandler mClickHandler;
    private Context context;

    public MovieDataAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieDataAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.grid_view_item, parent, false);
        return new MovieDataAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDataAdapterViewHolder holder, int position) {
        int clickedPosition = holder.getAdapterPosition();
        String imageUrl = mMovieDataList.get(clickedPosition).getMoviePosterUrl();
        MovieDataUtil.loadImageView(context, holder.mMoviePosterImageView, imageUrl);
    }

    @Override
    public int getItemCount() {
        if (mMovieDataList == null || mMovieDataList.isEmpty())
            return 0;
        return mMovieDataList.size();
    }

    public void setMovieData(List<MovieData> movieDataList) {
        mMovieDataList = movieDataList;
        notifyDataSetChanged();
    }

    public interface MovieAdapterOnClickHandler {
        void mClick(MovieData movieData);
    }

    /**
     * Cache of the children views for a movie data list item.
     */
    public class MovieDataAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mMoviePosterImageView;

        public MovieDataAdapterViewHolder(View view) {
            super(view);
            mMoviePosterImageView = (ImageView) view.findViewById(R.id.grid_poster_image_view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mClickHandler.mClick(mMovieDataList.get(clickedPosition));
        }
    }
}
