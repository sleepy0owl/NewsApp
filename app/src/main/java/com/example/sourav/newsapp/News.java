package com.example.sourav.newsapp;

public class News {
    private String mTitle;
    private String mWebUrl;
    private String mApiUrl;
    private String mPublishedDate;

    public News(String title, String webUrl, String apiUrl, String date){
        mTitle = title;
        mWebUrl = webUrl;
        mApiUrl = apiUrl;
        mPublishedDate = date;
    }

    //getter methods
    public String getTitle(){
        return mTitle;
    }

    public String getWebUrl(){
        return mWebUrl;
    }

    public String getApiUrl(){
        return mApiUrl;
    }

    public String getPublishedDate(){
        return mPublishedDate;
    }
}
