package com.example.csaenz.nba_news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by csaenz on 4/8/2017.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private static final int READ_TIMEOUT = 1000;

    private static final int CONNECT_TIMEOUT = 15000;

    private static final int SUCCESSFUL_REQUEST = 200;

    private static final String REQUEST_METHOD = "GET";

    /**
     * Verify network connectivity
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        } else return true;
    }

    /**
     * Create URL
     */

    public static URL createURL(String path, String key) {
        URL url;

        try {
            String string = path + key;
            url = new URL(string);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL", e);
            return null;
        }

        return url;
    }

    /**
     *  Make HTTP Request
     */

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(REQUEST_METHOD);
            urlConnection.setReadTimeout(READ_TIMEOUT /* milliseconds */);
            urlConnection.setConnectTimeout(CONNECT_TIMEOUT /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == SUCCESSFUL_REQUEST) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "HTTP Request was not successful, Response Code: " + urlConnection.getResponseCode());
            }

        } catch (IOException e) {
            // Handle the exception
            Log.e(LOG_TAG, "Problem with retrieving JSON", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Story} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Story> extractFromJson(String json_string) {

        // Create ArrayList to return later
        ArrayList<Story> stories = new ArrayList<>();

        //  Try to parse the JSON Response String. If there's a problem with the way the JSON
        //  is formatted, a JSONException exception object will be thrown.
        try {
            //  build up a list of books objects with the corresponding data.
            JSONObject rootObject = new JSONObject(json_string);

            JSONObject responseObject = rootObject.getJSONObject("response");

            JSONArray resultsArray = responseObject.getJSONArray("results");

            for (int i = 0; i < responseObject.length(); i++) {

                JSONObject itemObject = resultsArray.getJSONObject(i);

                String title = itemObject.getString("webTitle");

                String section = itemObject.getString("sectionName");

                String date = itemObject.getString("webPublicationDate");

                date = date.substring(0, 10);

                String url = itemObject.getString("webUrl");

                //add data to ArrayList
                stories.add(new Story(title, section, date, url));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("QueryUtils", "Problem parsing the Books results", e);
        }

        // Return the ArrayList
        return stories;
    }
}
