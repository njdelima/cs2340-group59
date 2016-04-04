package ga.neerajdelima.themovieapp.model.network;

/**
 * This interface should be implemented by any class that calls
 * UserModel.processLogin().
 *
 * Based on the result, that method will call onProcessLoginSuccess() or
 * onProcessLoginFailure()
 *
 * Created by Neeraj on 2/29/16.
 */
public interface ProcessLoginResponse {
    /**
     * Process login as normal user
     * @param username username
     */
    void onProcessLoginSuccess(String username);
    /**
     * Process login as admin
     * @param username username
     */
    void onProcessLoginAsAdmin(String username);

    /**
     * Process login as banned user
     */
    void onProcessLoginBan();

    /**
     * Process login as locked user
     */
    void onProcessLoginLocked();

    /**
     * Process login failure
     */
    void onProcessLoginFailure();
}
