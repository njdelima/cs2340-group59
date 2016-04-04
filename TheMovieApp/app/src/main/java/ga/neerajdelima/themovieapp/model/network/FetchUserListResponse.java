package ga.neerajdelima.themovieapp.model.network;

import java.util.List;

import ga.neerajdelima.themovieapp.model.User;

/**
 * Created by Joshua on 3/14/16.
 */
public interface FetchUserListResponse {
<<<<<<< HEAD
    /**
     * Send userList to onFetchUserListComplete
     * @param userList list of users
     */
    void onFetchUserListComplete(ArrayList<User> userList);
=======
    void onFetchUserListComplete(List<User> userList);
>>>>>>> master
}
