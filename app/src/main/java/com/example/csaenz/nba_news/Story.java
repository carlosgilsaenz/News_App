package com.example.csaenz.nba_news;

/**
 * Created by csaenz on 4/7/2017.
 */

public class Story {

    String mTitle;

    String mSection;

    String mUrl;

    String mDate;

    public Story(String title, String section, String date, String url) {
        mTitle = title;
        mSection = section;
        mDate = date;
        mUrl = url;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDate() {
        return mDate;
    }

    public String getSection() {
        return mSection;
    }

    public String getTitle() {
        return mTitle;
    }
}
