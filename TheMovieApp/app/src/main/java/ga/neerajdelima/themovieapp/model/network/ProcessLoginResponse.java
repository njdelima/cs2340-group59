package ga.neerajdelima.themovieapp.model.network;

/**
 * Created by Neeraj on 2/29/16.
 */
public interface ProcessLoginResponse {
    void onProcessLoginSuccess(String username);
    void onProcessLoginFailure();
}
