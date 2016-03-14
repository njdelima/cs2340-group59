package ga.neerajdelima.themovieapp.model.network;

import java.util.ArrayList;

import ga.neerajdelima.themovieapp.model.User;

/**
 * Created by Joshua on 3/14/16.
 */
public interface FetchUserListResponse {
    void onFetchUserListComplete(ArrayList<User> userList);
}
