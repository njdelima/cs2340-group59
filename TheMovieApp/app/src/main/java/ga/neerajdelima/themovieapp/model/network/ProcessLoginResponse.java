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
    void onProcessLoginSuccess(String username);
    void onProcessLoginFailure();
}
