package com.nullpointexecutioners.buzzfilms.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.github.florent37.picassopalette.PicassoPalette;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.view.IconicsImageView;
import com.nullpointexecutioners.buzzfilms.R;
import com.nullpointexecutioners.buzzfilms.Review;
import com.nullpointexecutioners.buzzfilms.adapters.ReviewAdapter;
import com.nullpointexecutioners.buzzfilms.helpers.SessionManager;
import com.nullpointexecutioners.buzzfilms.helpers.StringHelper;
import com.nullpointexecutioners.buzzfilms.helpers.ViewHelper;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieDetailActivity extends AppCompatActivity {

    @Bind(android.R.id.content) View thisActivity;
    @Bind(R.id.movie_critic_score) TextView movieCriticScore;
    @Bind(R.id.movie_critic_score_icon) IconicsImageView movieCriticScoreIcon;
    @Bind(R.id.movie_detail_toolbar) Toolbar toolbar;
    @Bind(R.id.movie_poster) ImageView moviePoster;
    @Bind(R.id.movie_release_date) TextView movieReleaseDate;
    @Bind(R.id.movie_synopsis) TextView movieSynopsis;
    @Bind(R.id.movie_synopsis_icon) IconicsImageView movieSynopsisIcon;
    @Bind(R.id.movie_title) TextView movieTitle;
    @Bind(R.id.release_date_icon) IconicsImageView releaseDateIcon;
    @Bind(R.id.review_fab) FloatingActionButton floatingActionButton;
    @Bind(R.id.toolbar_layout) CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.user_reviews_button) Button userReviewsButton;
    @BindInt(R.color.accent) int accentColor;
    @BindInt(R.color.primary_text_light) int primaryTextLightColor;
    @BindString(R.string.cancel) String cancel;
    @BindString(R.string.leave_review_title) String leaveReviewTitle;
    @BindString(R.string.neat) String neat;
    @BindString(R.string.save) String save;
    @BindString(R.string.user_reviews) String userReviewsTitle;

    private final Firebase mReviewRef = new Firebase("https://buzz-films.firebaseio.com/reviews");
    private int movieColor;
    private ReviewAdapter mReviewAdapter;
    private String mMovieId;
    private String posterURL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        final Intent intent = getIntent();
        final Bundle bundle = intent.getExtras();

        if (bundle != null) {
            mMovieId = (String) bundle.get("id");

            final String mMovieTitle = (String) bundle.get("title");
            movieTitle.setText(mMovieTitle);

            final String releaseDate = (String) bundle.get("release_date");
            try { //try to parse the release dates to be the Locale default (in our case, 'murica)
                final SimpleDateFormat fromDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                final SimpleDateFormat toDate = new SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault());
                movieReleaseDate.setText(toDate.format(fromDate.parse(releaseDate)));
            } catch (ParseException pe) {
                movieReleaseDate.setText(releaseDate);
            }
            movieCriticScore.setText(String.format(Locale.getDefault(), "%1$.1f", (Double) bundle.get("critics_score")));
            movieCriticScore.append(" / 10"); //outta ten

            movieSynopsis.setText((String) bundle.get("synopsis"));

            posterURL = StringHelper.getPosterUrl((String) bundle.get("poster_path"));
            //used for getting colors from the movie poster
            Picasso.with(this).load(posterURL).into(moviePoster,
                    PicassoPalette.with(posterURL, moviePoster)
                            .intoCallBack(new PicassoPalette.CallBack() {
                                @Override
                                public void onPaletteLoaded(Palette palette) {
                                    movieColor = colorSelector(palette);
                                    //because the support library doesn't allow us to change the background color of the FAB, we just tint it instead
                                    floatingActionButton.setBackgroundTintList(new ColorStateList(new int[][]{new int[]{0}}, new int[]{movieColor}));
                                    releaseDateIcon.setColor(movieColor);
                                    movieCriticScoreIcon.setColor(movieColor);
                                    movieSynopsisIcon.setColor(movieColor);
                                    userReviewsButton.setTextColor(movieColor);

                                    collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(getColor(R.color.primary)));
                                    collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(getColor(R.color.primary_dark)));
                                }
                            }));
            //Hide the title until the toolbar is collapsed
            collapsingToolbarLayout.setTitle(mMovieTitle);
            collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        }

        final int SIZE_DP = 24;
        final int PADDING_DP = 2;
        final Drawable addReviewIcon = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_add)
                .color(Color.BLACK)
                .sizeDp(SIZE_DP)
                .paddingDp(PADDING_DP);
        floatingActionButton.setImageDrawable(addReviewIcon);

        initToolbar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * Handles leaving a review
     */
    @OnClick(R.id.review_fab)
    public void leaveReview() {
        //get current username and major
        final String CURRENT_USER = SessionManager.getInstance(MovieDetailActivity.this).getLoggedInUsername();
        final String MAJOR = SessionManager.getInstance(MovieDetailActivity.this).getLoggedInMajor();

        final MaterialDialog reviewDialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                .title(leaveReviewTitle)
                .customView(R.layout.rating_movie_dialog, true)
                .theme(Theme.DARK)
                .positiveText(save)
                .negativeText(cancel)
                .positiveColor(movieColor)
                .negativeColor(movieColor)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog reviewDialog, @NonNull DialogAction which) {
                        final RatingBar ratingBar = ButterKnife.findById(reviewDialog, R.id.rating_bar);
                        final double rating = ratingBar.getRating(); //get the rating

                        /*Store the review to Firebase, using the movieId*/
                        mReviewRef.child(mMovieId).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final Firebase movieReviewRef = mReviewRef.child(mMovieId);
                                //store unique movieId if there isn't one already
                                if (dataSnapshot.child("movieId").getValue(String.class) == null) {
                                    movieReviewRef.child("movieId").setValue(mMovieId);
                                }

                                //store posterURL if there isn't one already
                                if (dataSnapshot.child("posterURL").getValue(String.class) == null) {
                                    movieReviewRef.child("posterURL").setValue(posterURL);
                                }

                                final Firebase userReviewRef = movieReviewRef.child(CURRENT_USER);
                                userReviewRef.child("username").setValue(CURRENT_USER);
                                userReviewRef.child("major").setValue(MAJOR);
                                userReviewRef.child("rating").setValue(rating);
                                ViewHelper.makeSnackbar(thisActivity, getString(R.string.review_submitted), Snackbar.LENGTH_LONG,
                                        accentColor, primaryTextLightColor).show();

                            }
                            @Override
                            public void onCancelled(FirebaseError firebaseError) {
                            }
                        });
                    }
                }).build();
        //Leave review as {current_username}
        final TextView reviewee = ButterKnife.findById(reviewDialog, R.id.reviewee);

        reviewee.append(" " + CURRENT_USER); //bold the username text

        reviewDialog.show();
    }

    /**
     * Gets all reviews for a particular movie and displays them in a dialog box
     */
    @OnClick(R.id.user_reviews_button)
    public void setupReviews() {
        final MaterialDialog reviewsDialog = new MaterialDialog.Builder(MovieDetailActivity.this)
                .title(userReviewsTitle)
                .customView(R.layout.movie_reviews_dialog, false)
                .theme(Theme.DARK)
                .positiveText(neat)
                .positiveColor(movieColor)
                .build();

        final ListView movieReviewsList = ButterKnife.findById(reviewsDialog, R.id.movie_reviews_list);

        mReviewRef.child(mMovieId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final ArrayList<Review> reviews = new ArrayList<>();

                //iterate through all of the reviews for the movie
                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    if (("posterURL").equals(child.getKey()) || ("movieId").equals(child.getKey())) {
                        continue;
                    }
                    final String username = child.child("username").getValue(String.class);
                    final String major = child.child("major").getValue(String.class);
                    Double rating = child.child("rating").getValue(Double.class);

                    reviews.add(new Review(username, major, rating));
                }
                if (!reviews.isEmpty()) {
                    mReviewAdapter = new ReviewAdapter(MovieDetailActivity.this,
                            R.layout.review_list_item, new ArrayList<Review>());
                    movieReviewsList.setAdapter(mReviewAdapter);
                    mReviewAdapter.addAll(reviews);
                    reviews.clear();
                } else {
                    //Display a hint stating there are no reviews
                    TextView noReviewsHint = ButterKnife.findById(reviewsDialog, R.id.no_reviews_hint);
                    movieReviewsList.setVisibility(View.GONE);
                    noReviewsHint.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

        reviewsDialog.show(); //finally show the dialog
    }

    /**
     * Expands the poster when user clicks on it
     */
    @OnClick(R.id.movie_poster)
    public void showFullMoviePoster() {
        startActivity(new Intent(MovieDetailActivity.this, MoviePosterActivity.class).putExtra("posterURL", posterURL).putExtra("color", movieColor));
    }

    /**
     * Helper method that inits all of the Toolbar stuff
     */
    private void initToolbar() {
        assert getSupportActionBar() != null;
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); //Simulate a system's "Back" button functionality.
            }
        });
    }

    /**
     * Helper method for determining which color to use for the toolbar
     * Since the palette won't always be able to get one of each color--this method returns the one
     * that isn't the default color (in this case, @color/accent)
     * @param palette of generate colors from the movie poster
     * @return selected color
     */
    private int colorSelector(Palette palette) {
        int defaultColor = getThemeAccentColor(MovieDetailActivity.this); //primary color
        int vibrant = palette.getVibrantColor(defaultColor);
        int vibrantLight = palette.getLightVibrantColor(defaultColor);
        int mutedLight = palette.getLightMutedColor(defaultColor);

        if (vibrant != defaultColor) {
            return vibrant;
        } else if (vibrantLight != defaultColor) {
            return vibrantLight;
        } else if (mutedLight != defaultColor) {
            return mutedLight;
        } else {
            return defaultColor;
        }
    }

    /**
     * Helper method for getting the current app's accent color
     * @param context from which to get the color
     * @return int value of color
     */
    private int getThemeAccentColor(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }
}
