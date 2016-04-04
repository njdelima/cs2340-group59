package ga.neerajdelima.themovieapp.model;

/**
 * Created by Joshua on 3/7/16.
 */
public class Movie implements Comparable<Movie> {
    private String imdbID;
    private String title;
    private int totalRating;
    private int ratingCount;

    public Movie(String imdbID, String title, int totalRating, int ratingCount) {
        this.imdbID = imdbID;
        this.title = title;
        this.totalRating = totalRating;
        this.ratingCount = ratingCount;
    }

    public String getImdbID() {
        return this.imdbID;
    }
    public int getTotalRating() {
        return this.totalRating;
    }
    public int getRatingCount() {
        return this.ratingCount;
    }
    public String getTitle() {
        return this.title;
    }
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }
    public void setRatingCount(int ratingCount) { this.ratingCount = ratingCount; }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {return imdbID.hashCode() + title.hashCode();}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Movie)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Movie that = (Movie) obj;
        return ( (this.getImdbID().equals(that.getImdbID()) ) &&
                ( this.getTotalRating() == that.getTotalRating() &&
                  this.getRatingCount() == that.getRatingCount() &&
                  this.getTitle() == that.getTitle()) );
    }

    @Override
    public int compareTo(Movie o) {
        if (o == this) {
            return 0;
        }

        double thisAverageRating = this.totalRating / this.ratingCount;
        double thatAverageRating = o.totalRating / o.ratingCount;

        if (thisAverageRating < thatAverageRating) {
            return -1;
        } else if (thisAverageRating > thatAverageRating) {
            return 1;
        }
        return 0;
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
