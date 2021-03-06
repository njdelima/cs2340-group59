package ga.neerajdelima.themovieapp.model.network;

import android.os.AsyncTask;
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

    /**
     * Constructor for POST requests.
     * At the moment, POST is only used to communicate with the
     * database. Open movie database API only uses GET requests.
     *
     * This constructor just opens a connection to requestURL
     * and stores it in the connection variable.
     * After the constructor is called you can call all of the
     * below instance methods like sendPostData() to POST data
     * to the URL and after that getInputString() to get the
     * server's response.
     *
     * @param requestURL URL
     */
    public FetchTask(String requestURL) {
        try {
            final URL url = new URL(requestURL);
            connection = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            //Log.d("MalformedURLException", e.getStackTrace().toString());
            return;
        } catch (IOException e) {
            //Log.d("IOException", e.getStackTrace().toString());
            return;
        }
    }

    /**
     * Empty constructor for GET requests
     * After the constructor is called you can call all of
     * the below instance methods like sendGetData() to send
     * a GET request to a URL and after that getInputString() to
     * get the server's response.
     */
    public FetchTask() {

    }

    /**
     * Sends a GET request to the url with the params.
     * for example calling
     * sendGetData("https://google.com/search", "q=CS2340")
     * is equivalent to https://google.com/search?q=CS2340
     *
     * @param requestURL URL
     * @param params name of the movie
     */
    protected void sendGetData(String requestURL, String params) {
        try {
            final String newrequestURL = requestURL + "?" + params;
            final URL url = new URL(newrequestURL);
            connection = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            //Log.d("MalformedURLException", e.getStackTrace().toString());
            return;
        } catch (IOException e) {
            //Log.d("IOException", e.getStackTrace().toString());
            return;
        }
    }
    /**
     * POSTS jsonData to the URL currently connected to by the
     * connection HTTPURLConnection.
     * After you call this method, you can call getResponseCode()
     * or getResponseMessage() to see if it was successful.
     * @param jsonData JSON data
     */
    protected void sendPostData(JSONObject jsonData) {
        try {
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            final byte[] postData = jsonData.toString().getBytes();

            connection.getOutputStream().write(postData);
        } catch (IOException e) {
            //Log.d("te real ioexception", "Test");
            //Log.d("IOException", e.getStackTrace().toString());
            return;
        }
    }

    /**
     * Gets the response code. 200 is good, most anything else
     * means there's a mistake somewhere
     * @return response code
     */
    protected int getResponseCode() {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            //Log.d("IOException", e.getMessage());
            return -1;
        }
    }
    /**
     * Gets the response message i.e. a 200 would be "OK"
     * a 404 would be "Not Found" etc.
     * @return response message
     */
    protected String getResponseMessage() {
        try {
            return connection.getResponseMessage();
        } catch (IOException e) {
            //Log.d("IOException", e.getMessage());
            return null;
        }
    }
    /**
     * Gets the response from the server as an InputStream
     * not really used because InputStreams aren't useful. see below
     * @return response from the server as an InputStream
     */
    protected InputStream getInputStream() {
        try {
            return connection.getInputStream();
        } catch (IOException e) {
            Log.d("IOException", e.getMessage());
        }
        return null;
    }

    /**
     * Gets the response from the server as String
     * @return response from the server as String
     */
    protected String getInputString() {
        try {
            return convertStreamToString(connection.getInputStream());
        } catch (IOException e) {
            Log.d("IOException", e.getStackTrace().toString());
        }
        return null;
    }

    /**
     * Gets the response from the server as an JSONObject
     * @return response from the server as an JSONObject
     */
    protected JSONObject getInputJSON() {
        try {
            return new JSONObject(convertStreamToString(connection.getInputStream()));
        } catch (IOException e) {
            Log.d("IOException", e.getStackTrace().toString());
        } catch (JSONException e) {
            Log.d("JSONException", e.getStackTrace().toString());
        }
        return null;
    }

    /**
     * Convert stream to string
     * @param is stream to be converted
     * @return converted stream
     */
    private String convertStreamToString(InputStream is) {
        final Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}