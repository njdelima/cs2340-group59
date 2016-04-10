package com.nullpointexecutioners.buzzfilms;

/**/
public class Movie {
    private String id;
    private String title;
    private String releaseDate;
    private String synopsis;
    private String posterUrl;
    private double criticsScore;

    /**
     * Constructor for Movie object
     * @param id unique to movie
     * @param title of movie
     * @param releaseDate of movie
     * @param synopsis of movie
     * @param posterUrl of movie
     * @param criticsScore of movie
     */
    public Movie(String id, String title, String releaseDate, String synopsis, String posterUrl, double criticsScore) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.synopsis = synopsis;
        this.posterUrl = posterUrl;
        this.criticsScore = criticsScore;
    }

    /**
     * to get id of a movie
     * @return id of a movie
     */
    public String getId() {
        return id;
    }

    /**
     * to get title of a movie
     * @return string title of a movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * get the releasedate of a movie
     * @return the date of a movie
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * to get the synopsis of a movie
     * @return get synopsos
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * get url of movie
     * @return get the url
     */
    public String getPosterUrl() {
        return posterUrl;
    }

    /**
     * get critics score of a movie
     * @return get critics score
     */
    public double getCriticsScore() {
        return criticsScore;
    }

    @Override
    public String toString() {
        return title + ", " + synopsis + ", " + criticsScore;
    }
}
