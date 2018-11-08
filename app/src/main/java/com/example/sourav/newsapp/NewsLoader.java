package com.example.sourav.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private static final String LOG_TAG = NewsLoader.class.getName();

    private String mQueryUrl;

    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        mQueryUrl = url;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<News> loadInBackground() {
        if (mQueryUrl == null){
            return null;
        }

        List<News> news = QueryUtils.fetchNews(mQueryUrl);

        return news;
    }
}
