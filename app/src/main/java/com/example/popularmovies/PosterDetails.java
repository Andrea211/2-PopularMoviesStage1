package com.example.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropTransformation;

public class PosterDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);

        TextView originalTitle = findViewById(R.id.titleTextView);
        TextView releaseDate = findViewById(R.id.releaseDateTextView);
        TextView overview = findViewById(R.id.overviewTextView);
        ImageView posterImage = findViewById(R.id.posterImageView);
        RatingBar ratingStars = findViewById(R.id.rating_stars);
        TextView voteCount = findViewById(R.id.vote_count);
        TextView originalLanguage = findViewById(R.id.original_language);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        Poster poster = intent.getParcelableExtra("poster");

        // TITLE
        originalTitle.setText(poster.getOriginalTitle());

        double rating = poster.getVoterAverage() / 2;
        ratingStars.setRating((float) rating);

        // IMAGE
        Picasso.with(this)
                .load(poster.getPosterPath())
                .fit()
                .transform(new CropTransformation(3000, 1000, CropTransformation.GravityHorizontal.CENTER, CropTransformation.GravityVertical.TOP))
                .error(R.mipmap.icon_filmroll_round)
                .placeholder(R.mipmap.icon_filmroll_round)
                .into(posterImage);

        // OVERVIEW

        overview.setText(poster.getOverview());
        overview.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);

        // RELEASE DATE
        releaseDate.setText(poster.getReleaseDate());

        // VOTE COUNT
        double doubleVoteCount = poster.getVoteCount();
        int intVoteCount = (int) doubleVoteCount;
        voteCount.setText("(" + String.valueOf(intVoteCount) + ")");

        // ORIGINAL LANGUAGE
        originalLanguage.setText(poster.getOriginalLanguage());

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, "Something went wrong.", Toast.LENGTH_SHORT).show();
    }
}
