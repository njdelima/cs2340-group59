package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ga.neerajdelima.themovieapp.model.User;

/**
 * Created by Joshua on 3/14/16.
 */
public class FetchUserListTask extends FetchTask {
    private FetchUserListResponse delegate;
    /**
     * Constructor of FetchUserListTask
     */
    public FetchUserListTask() {
        super("http://128.61.104.207:2340/api/users/list.php");
    }

    @Override
    protected JSONArray doInBackground(Object... args) {
        try {
            getConnection().setConnectTimeout(0);
            sendPostData(new JSONObject());

            Log.d("response", getResponseMessage());
            JSONArray response = new JSONArray(getInputString());
            Log.d("userlist", response.toString());
            return response;
        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object response) {
        JSONArray serverResponse = (JSONArray) response;

        ArrayList<User> userList = new ArrayList<User>();

        for (int i = 0; i < serverResponse.length(); i++) {
            Log.d("logging", "USER LIST");
            String username = serverResponse.optJSONObject(i).optString("username");
            Log.d("username", username);
            String password = serverResponse.optJSONObject(i).optString("password");
            Log.d("password", password);
            String major = serverResponse.optJSONObject(i).optString("major");
            Log.d("major", major);
            String firstName = serverResponse.optJSONObject(i).optString("first_name");
            Log.d("firstname", firstName);
            String lastName = serverResponse.optJSONObject(i).optString("last_name");
            Log.d("lastname", lastName);

            String adminstr = serverResponse.optJSONObject(i).optString("admin");
            Log.d("admin", String.valueOf(adminstr));
            boolean admin = "1".equals(adminstr);

            String lockedstr = serverResponse.optJSONObject(i).optString("locked");
            Log.d("locked", String.valueOf(lockedstr));
            Log.d("locked server resp", serverResponse.optJSONObject(i).optString("locked"));
            boolean locked = "1".equals(lockedstr);

            String bannedstr = serverResponse.optJSONObject(i).optString("banned");
            Log.d("banned", String.valueOf(bannedstr));
            boolean banned = "1".equals(bannedstr);

            User user = new User(username, password, firstName, lastName, major, locked, banned, admin);

            userList.add(user);
        }
        delegate.onFetchUserListComplete(userList);
    }
}
