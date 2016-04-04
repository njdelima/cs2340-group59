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
    public FetchUserListResponse delegate;

    public FetchUserListTask() {
        super("http://128.61.104.207:2340/api/users/list.php");
    }

    @Override
    protected JSONArray doInBackground(Object... args) {
        try {
            connection.setConnectTimeout(0);
            sendPostData(new JSONObject());

            Log.d("response", getResponseMessage());
            final JSONArray response = new JSONArray(getInputString());
            Log.d("userlist", response.toString());
            return response;
        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object response) {
        final JSONArray serverResponse = (JSONArray) response;

        final ArrayList<User> userList = new ArrayList<User>();

        for (int i = 0; i < serverResponse.length(); i++) {
            Log.d("logging", "USER LIST");
            final String username = serverResponse.optJSONObject(i).optString("username");
            Log.d("username", username);
            final String password = serverResponse.optJSONObject(i).optString("password");
            Log.d("password", password);
            final String major = serverResponse.optJSONObject(i).optString("major");
            Log.d("major", major);
            final String firstName = serverResponse.optJSONObject(i).optString("first_name");
            Log.d("firstname", firstName);
            final String lastName = serverResponse.optJSONObject(i).optString("last_name");
            Log.d("lastname", lastName);

            final String adminstr = serverResponse.optJSONObject(i).optString("admin");
            Log.d("admin", String.valueOf(adminstr));
            final boolean admin = "1".equals(adminstr);

            final String lockedstr = serverResponse.optJSONObject(i).optString("locked");
            Log.d("locked", String.valueOf(lockedstr));
            Log.d("locked server resp", serverResponse.optJSONObject(i).optString("locked"));
            final boolean locked = "1".equals(lockedstr);

            final String bannedstr = serverResponse.optJSONObject(i).optString("banned");
            Log.d("banned", String.valueOf(bannedstr));
            final boolean banned = "1".equals(bannedstr);

            final User user = new User(username, password, firstName, lastName, major, locked, banned, admin);

            userList.add(user);
        }
        delegate.onFetchUserListComplete(userList);
    }
}
