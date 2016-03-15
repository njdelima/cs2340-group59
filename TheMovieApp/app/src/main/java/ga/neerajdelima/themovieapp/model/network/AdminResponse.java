package ga.neerajdelima.themovieapp.model.network;

/**
 * Created by komalhirani on 3/15/16.
 */
public interface AdminResponse {
    void onProcessAdminSuccess(String username, int set);
    void onProcessAdminFailure();
}
