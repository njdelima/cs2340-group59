package ga.neerajdelima.themovieapp.model.network;

/**
 * This interface should be implemented by any class that calls
 * UtilitiesModel.checkNetworkConnection().
 *
 * Created by Network on 2/29/16.
 */
public interface NetworkCheckResponse {
    /**
     * This is the method that will be called if the network check
     * was successful.
     */
    void onNetworkCheckSuccess();

    /**
     * This method will be called if the network check
     * failed.
     */
    void onNetworkCheckFailure();
}