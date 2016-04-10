package com.nullpointexecutioners.buzzfilms;

public class Review {

    private String username;
    private String major;
    private double rating;

    /**
     * Constructor for a review
     * @param username of person who left review
     * @param major of user who left review
     * @param rating of movie
     */
    public Review(String username, String major, double rating) {
        this.username = username;
        this.major = major;
        this.rating = rating;
    }

    /**
     * Getter for retrieving the username of the user who left the review
     * @return username of user
     */
    public String getReviewUser() {
        return username;
    }

    /**
     * Getter for retrieving the major of the user who left the review
     * @return major of user
     */
    public String getReviewMajor() {
        return major;
    }

    /**
     * Getter for retrieving the rating of the review
     * @return rating of review
     */
    public double getReviewRating() {
        return rating;
    }
}
