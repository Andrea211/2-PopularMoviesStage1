package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.*;
import android.view.ViewGroup;

import com.squareup.picasso.*;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final Poster[] mPosters;
    private final Context mContext;

    // Constructor for ImageAdapter
    public ImageAdapter(Context mContext, Poster[] mPosters) {
        this.mPosters = mPosters;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    // Creating new views - poster images holders, to populate recyclerView
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Create a new view
        ImageView imageView = (ImageView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poster_gridview, parent, false);

        return new ViewHolder(imageView);
    }

    @Override
    // Populate previously created views with data - poster images
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.with(mContext)
                .load(mPosters[position].getPosterPath())
                .fit()
                .error(R.mipmap.icon_filmroll_round)
                .placeholder(R.mipmap.icon_filmroll_round)
                .into((ImageView) holder.mImageView.findViewById(R.id.image_view));

        // Go to details view after clicking the poster
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, PosterDetails.class);
            intent.putExtra("poster", mPosters[position]);
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (mPosters == null || mPosters.length == 0) {
            return -1;
        }
        return mPosters.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        private ViewHolder(ImageView v) {
            super(v);
            mImageView = v;
        }
    }
}
