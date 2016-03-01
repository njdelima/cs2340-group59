package ga.neerajdelima.themovieapp.model.network;


/**
 * This interface should be implemented by any class that calls
 * RatingsModel.getMovieInfo().
 *
 * The results of that call will be passed as params to onFetchMovieInfoResponse()
 * Created by Joshua on 2/29/16.
 */
public interface FetchMovieInfoResponse {
    void onFetchMovieInfoResponse(String title, String year, String rated,
                                  String released, String runtime,
                                  String genre, String director, String writer,
                                  String actors, String plot, String language,
                                  String country, String awards);
}
