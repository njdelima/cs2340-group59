package com.nullpointexecutioners.buzzfilms.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nullpointexecutioners.buzzfilms.R;
import com.nullpointexecutioners.buzzfilms.Review;

import java.util.List;
import java.util.Locale;

public class ReviewAdapter extends ArrayAdapter<Review> {

    Context context;
    int layoutResourceId;
    List<Review> reviews;

    /**
     * Constructor for ReviewAdapter
     * @param context for adapter
     * @param layoutResourceId layout
     * @param reviews data
     */
    public ReviewAdapter(Context context, int layoutResourceId, List<Review> reviews) {
        super(context, layoutResourceId, reviews);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.reviews = reviews;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ReviewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ReviewHolder();
            holder.reviewUser = (TextView) row.findViewById(R.id.review_user);
            holder.reviewMajor = (TextView) row.findViewById(R.id.review_major);
            holder.reviewRating = (TextView) row.findViewById(R.id.review_rating);

            row.setTag(holder);
        } else {
            holder = (ReviewHolder) row.getTag();
        }

        Review review = reviews.get(position);
        holder.reviewUser.setText(review.getReviewUser());
        holder.reviewMajor.setText(review.getReviewMajor());
        holder.reviewRating.setText(String.format(Locale.getDefault(), "%.1f", review.getReviewRating()));

        return row;
    }

    static class ReviewHolder {
        TextView reviewUser;
        TextView reviewMajor;
        TextView reviewRating;
    }
}
