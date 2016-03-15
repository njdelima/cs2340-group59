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
            String username = serverResponse.optJSONObject(i).optString("username");
            String password = serverResponse.optJSONObject(i).optString("password");
            String major = serverResponse.optJSONObject(i).optString("major");
            String firstName = serverResponse.optJSONObject(i).optString("first_name");
            String lastName = serverResponse.optJSONObject(i).optString("last_name");
            boolean admin = serverResponse.optJSONObject(i).optBoolean("admin");
            boolean locked = serverResponse.optJSONObject(i).optBoolean("locked");
            boolean banned = serverResponse.optJSONObject(i).optBoolean("banned");

            User user = new User(username, password, firstName, lastName, major, locked, banned, admin);

            userList.add(user);
        }
        delegate.onFetchUserListComplete(userList);
    }
}
