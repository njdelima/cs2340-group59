package ga.neerajdelima.themovieapp.model.network;

/**
 * Created by komalhirani on 3/15/16.
 */
public interface LockResponse {
    void onProcessLockSuccess(String username, int set);
    void onProcessLockFailure();
}
