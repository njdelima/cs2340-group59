package ga.neerajdelima.themovieapp.model.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Joshua on 3/14/16.
 */
public class FetchUserListTask extends FetchTask {
    public FetchUserListResponse delegate;

    public FetchUserListTask() {
        super("http://128.61.104.207:2340/api/users/list.php");
    }

    @Override
    protected JSONObject doInBackground(Object... args) {
        try {
            sendPostData(new JSONObject());

            Log.d("response", getResponseMessage());
            JSONObject response = new JSONObject(getInputString());
            Log.d("userlist", response.toString());
        } catch (JSONException e) {
            Log.d("JsonException", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object response) {
    }
}
