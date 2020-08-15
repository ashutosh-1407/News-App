package com.example.android.mynewsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.AsyncTaskLoader;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<NewsItem>> {

    private static final String GUARDIAN_NEWS_URL = "http://content.guardianapis.com/search?q=debates&api-key=test";

    private NewsItemAdapter newsItemAdapter = null;

    TextView emptyView;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_view);

        newsItemAdapter = new NewsItemAdapter(this, new ArrayList<NewsItem>());

        listView.setAdapter(newsItemAdapter);

        emptyView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        progressBar = findViewById(R.id.progress_bar);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
        else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_internet);
        }

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsItemAdapter.getItem(i).getUrl()));
                startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public Loader<ArrayList<NewsItem>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsItemsLoader(this, GUARDIAN_NEWS_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<NewsItem>> loader, ArrayList<NewsItem> data) {
        emptyView.setText(R.string.no_news_item);
        progressBar.setVisibility(View.GONE);
        newsItemAdapter.clear();
        if (data != null || !data.isEmpty()) {
            newsItemAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<NewsItem>> loader) {
        newsItemAdapter.clear();
    }

}