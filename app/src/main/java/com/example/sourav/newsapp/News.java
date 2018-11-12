package com.example.sourav.newsapp;

public class News {
    private String mTitle;
    private String mWebUrl;
    private String mTrailText;
    private String mThumbnailUrl;
    private String mPublishedDate;
    private String mSectionName;
    private String mAuthorName;

    public News(String title, String webUrl, String thumbnailUrl, String trailText, String date, String section, String Author){
        mTitle = title;
        mWebUrl = webUrl;
        mThumbnailUrl = thumbnailUrl;
        mTrailText = trailText;
        mPublishedDate = date;
        mSectionName = section;
        mAuthorName = Author;
    }

    //getter methods
    public String getTitle(){
        return mTitle;
    }

    public String getWebUrl(){
        return mWebUrl;
    }

    public String getThumbnailUrl(){
        return mThumbnailUrl;
    }

    public String getTrailText(){
        return mTrailText;
    }

    public String getPublishedDate(){
        return mPublishedDate;
    }

    public String getSectionName(){
        return mSectionName;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

}
