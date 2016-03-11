package ga.neerajdelima.themovieapp.model;

/**
 * Created by Joshua on 3/7/16.
 */
public class Movie {
    private String imdbID;
    private String title;
    private int rating;

    public String getImdbID() {
        return this.imdbID;
    }
    public String getRating() {
        return this.rating;
    }
    public String getTitle() {
        return this.title;
    }
    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
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
                ( this.getRating() == that.getRating() ) ) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public String toString() {
        String s = "";
        s = s + "imdbID: " + this.imdbID + ", ";
        s = s + "rating: " + this.rating;
        return s;
    }
}
