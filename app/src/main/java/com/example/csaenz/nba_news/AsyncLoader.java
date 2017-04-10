package com.example.csaenz.nba_news;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.csaenz.nba_news.QueryUtils.createURL;
import static com.example.csaenz.nba_news.QueryUtils.extractFromJson;
import static com.example.csaenz.nba_news.QueryUtils.makeHttpRequest;

/**
 * Created by csaenz on 4/9/2017.
 */

public class AsyncLoader extends AsyncTaskLoader<ArrayList<Story>> {

    private static final String LOG_TAG = AsyncLoader.class.getSimpleName();

    private String mUrlString;

    private String mKey;

    public AsyncLoader(Context context, String url, String key) {
        super(context);
        mUrlString = url;
        mKey = key;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Story> loadInBackground() {

        ArrayList<Story> stories = new ArrayList<>();

        // Create URL
        URL url = createURL(mUrlString, mKey);

        // Ensure URL creation is not null
        // Exit from rest of method otherwise
        if (url == null) {
            return stories;
        }

        // Variable to use for HttpRequest
        String jsonResponse = "";

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to make HTTP Request", e);
        }

        if (jsonResponse.isEmpty() || jsonResponse.equals("")) {
            return stories;
        }

        stories = extractFromJson(jsonResponse);

        return stories;
    }
}
