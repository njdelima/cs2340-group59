package ga.neerajdelima.themovieapp.model.network;

/**
 * Created by komalhirani on 3/15/16.
 */
public interface BanResponse {
    void onProcessBanSuccess(String username, int set);
    void onProcessBanFailure();
}
