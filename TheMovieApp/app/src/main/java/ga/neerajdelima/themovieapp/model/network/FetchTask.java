package ga.neerajdelima.themovieapp.model.network;

import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Neeraj on 2/21/16.
 */
public abstract class FetchTask extends AsyncTask {
    protected HttpURLConnection connection;

    public FetchTask(String requestURL) {
        try {
            URL url = new URL(requestURL);
            connection = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            Log.d("Malformed URL Exception", e.getMessage());
        } catch (IOException e) {
            Log.d("IOException", e.getMessage());
        }
    }

    protected void sendPostData(JSONObject jsonData) {
        try {
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            byte[] postData = jsonData.toString().getBytes();

            connection.getOutputStream().write(postData);
        } catch (IOException e) {
            Log.d("te real ioexception", "Test");
            Log.d("IOException", e.getStackTrace().toString());
        }
    }

    protected int getResponseCode() {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            Log.d("IOException", e.getMessage());
        }
        return -1;
    }
    protected String getResponseMessage() {
        try {
            return connection.getResponseMessage();
        } catch (IOException e) {
            Log.d("IOException", e.getMessage());
        }
        return null;
    }
    protected InputStream getInputStream() {
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            Log.d("IOException", e.getMessage());
        }
        return null;
    }

    protected String getInputString() {
        try {
            return convertStreamToString(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}