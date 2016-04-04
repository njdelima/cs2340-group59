package ga.neerajdelima.themovieapp.model;

/**
 * Created by Joshua on 3/7/16.
 */
public class Movie implements Comparable<Movie> {
    private String imdbID;
    private String title;
    private int totalRating;
    private int ratingCount;

    /**
     * Constructor of Movie
     * @param imdbID imdbID
     * @param title movie title
     * @param totalRating total rating
     * @param ratingCount number of rating
     */
    public Movie(String imdbID, String title, int totalRating, int ratingCount) {
        this.imdbID = imdbID;
        this.title = title;
        this.totalRating = totalRating;
        this.ratingCount = ratingCount;
    }

    /**
     * @return imdbID
     */
    public String getImdbID() {
        return this.imdbID;
    }

    /**
     * @return total rating
     */
    public int getTotalRating() {
        return this.totalRating;
    }

    /**
     * @return number of ratings
     */
    public int getRatingCount() {
        return this.ratingCount;
    }

    /**
     * @return movie title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param imdbID imdbID to be set.
     */
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    /**
     * @param totalRating total rating is to be set.
     */
    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    /**
     * @param ratingCount number of ratings to be set.
     */
    public void setRatingCount(int ratingCount) { this.ratingCount = ratingCount; }

    /**
     * @param title movie title to be set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Movie)) return false;
        if (obj == this) return true;

        Movie that = (Movie) obj;
        if ( (this.getImdbID().equals(that.getImdbID()) ) &&
                ( this.getTotalRating() == that.getTotalRating() &&
                  this.getRatingCount() == that.getRatingCount() &&
                  this.getTitle() == that.getTitle()) ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Movie o) {
        if (o == null) throw new NullPointerException("Specified object is null.");
        if (o == this) return 0;

        double thisAverageRating = this.totalRating / this.ratingCount;
        double thatAverageRating = o.totalRating / o.ratingCount;

        if (thisAverageRating < thatAverageRating) {
            return -1;
        } else if (thisAverageRating > thatAverageRating) {
            return 1;
        } else if (thisAverageRating == thatAverageRating) {
            return 0;
        }
        return -10;
    }

    @Override
    public String toString() {
        String s = "";
        //s = s + "imdbID: " + this.imdbID + ", ";
        //s = s + "title: " + this.title + ", ";
        s = s + this.title + ",";
        s = s + "Average Rating: " + this.totalRating / this.ratingCount;
        return s;
    }
}
