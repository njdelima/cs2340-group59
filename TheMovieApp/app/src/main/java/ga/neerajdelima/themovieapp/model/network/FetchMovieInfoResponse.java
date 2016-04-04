package ga.neerajdelima.themovieapp.model.network;


/**
 * This interface should be implemented by any class that calls
 * RatingsModel.getMovieInfo().
 *
 * The results of that call will be passed as params to onFetchMovieInfoResponse()
 * Created by Joshua on 2/29/16.
 */
public interface FetchMovieInfoResponse {
    /**
     * Send all movie info to onFetchMovieInforResponse
     * @param title movie title
     * @param year movie year
     * @param rated movie rating
     * @param released movie released date
     * @param runtime movie runtime
     * @param genre movie genre
     * @param director movie director
     * @param writer movie writer
     * @param actors movie actors
     * @param plot movie plot
     * @param language language used in the movie
     * @param country country where the movie was made
     * @param awards awards the movie won
     * @param imdbID imdbID
     */
    void onFetchMovieInfoResponse(String title, String year, String rated,
                                  String released, String runtime,
                                  String genre, String director, String writer,
                                  String actors, String plot, String language,
                                  String country, String awards, String imdbID);
}
