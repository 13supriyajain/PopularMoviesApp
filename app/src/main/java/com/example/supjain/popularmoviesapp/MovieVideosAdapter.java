package com.example.supjain.popularmoviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * This custom Adapter class is for setting values and displaying contents of videos_list_item.xml layout file.
 */

public class MovieVideosAdapter extends RecyclerView.Adapter<MovieVideosAdapter.MovieVideosAdapterViewHolder> {

    private List<MovieVideosData> mMovieVideosDataList;
    private MovieVideosAdapterOnClickHandler mClickHandler;
    private Context context;

    public MovieVideosAdapter(MovieVideosAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieVideosAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.videos_list_item, parent, false);
        return new MovieVideosAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieVideosAdapterViewHolder holder, int position) {
        int viewPosition = holder.getAdapterPosition();
        String videoName = mMovieVideosDataList.get(viewPosition).getVideoName();
        holder.videoNameTextView.setText(videoName);
    }

    @Override
    public int getItemCount() {
        if (mMovieVideosDataList == null || mMovieVideosDataList.isEmpty())
            return 0;
        return mMovieVideosDataList.size();
    }

    public void setMovieVideosData(List<MovieVideosData> movieVideosDataList) {
        mMovieVideosDataList = movieVideosDataList;
        notifyDataSetChanged();
    }

    public interface MovieVideosAdapterOnClickHandler {
        void mClick(String movieVideosUrl);
    }

    /**
     * Cache of the children views for a movie videos data list item.
     */
    public class MovieVideosAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView videoNameTextView;
        public final ImageView videoShareBtn;

        public MovieVideosAdapterViewHolder(View view) {
            super(view);
            videoNameTextView = view.findViewById(R.id.video_title_textview);
            videoShareBtn = view.findViewById(R.id.video_share_btn);

            videoShareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Video Shared", Toast.LENGTH_SHORT).show();
                }
            });

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mClickHandler.mClick(mMovieVideosDataList.get(clickedPosition).getVideoUrl());
        }
    }
}
