package com.example.supjain.popularmoviesapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.supjain.popularmoviesapp.Data.MovieReviewsData;
import com.example.supjain.popularmoviesapp.R;

import java.util.List;

/**
 * This custom Adapter class is for setting values and displaying contents of reviews_list_item.xml layout file.
 */

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewsAdapterViewHolder> {

    private static final String COLON_SPACE = " :";
    private List<MovieReviewsData> mMovieReviewsDataList;
    private MovieReviewsAdapterOnClickHandler mClickHandler;
    private Context context;

    public MovieReviewsAdapter(MovieReviewsAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieReviewsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.reviews_list_item, parent, false);
        return new MovieReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewsAdapterViewHolder holder, int position) {
        int viewPosition = holder.getAdapterPosition();
        String reviewerName = mMovieReviewsDataList.get(viewPosition).getReviewerName();
        holder.reviewerNameTextView.setText(reviewerName + COLON_SPACE);

        String reviewContent = mMovieReviewsDataList.get(viewPosition).getReviewText();
        holder.reviewContentTextView.setText(reviewContent);
    }

    @Override
    public int getItemCount() {
        if (mMovieReviewsDataList == null || mMovieReviewsDataList.isEmpty())
            return 0;
        return mMovieReviewsDataList.size();
    }

    public void setMovieReviewsData(List<MovieReviewsData> movieReviewsDataList) {
        mMovieReviewsDataList = movieReviewsDataList;
        notifyDataSetChanged();
    }

    public interface MovieReviewsAdapterOnClickHandler {
        void mClick(String movieReviewUrl);
    }

    /**
     * Cache of the children views for a movie videos data list item.
     */
    public class MovieReviewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView reviewerNameTextView;
        public final TextView reviewContentTextView;
        public final ImageView reviewReadMoreBtn;

        public MovieReviewsAdapterViewHolder(View view) {
            super(view);
            reviewerNameTextView = view.findViewById(R.id.reviewer_name_textview);
            reviewContentTextView = view.findViewById(R.id.review_content_textview);
            reviewReadMoreBtn = view.findViewById(R.id.review_read_more_btn);
            reviewReadMoreBtn.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mClickHandler.mClick(mMovieReviewsDataList.get(clickedPosition).getReviewUrl());
        }
    }
}
