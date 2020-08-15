package com.example.android.mynewsapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;

public class NewsItemsLoader extends AsyncTaskLoader<ArrayList<NewsItem>> {

    private String mUrl;

    public NewsItemsLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<NewsItem> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        ArrayList<NewsItem> newsItems = QueryUtils.fetchNewsItems(mUrl);
        return newsItems;
    }
}
