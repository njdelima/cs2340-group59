package ga.neerajdelima.themovieapp.model.network;

import java.util.ArrayList;
import java.util.List;

import ga.neerajdelima.themovieapp.model.User;

/**
 * Created by Joshua on 3/14/16.
 */
public interface FetchUserListResponse {
    void onFetchUserListComplete(List<User> userList);
}
