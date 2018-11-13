package com.example.sourav.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    //this project shows user a sport news

    private static final String API_KEY = "56102ca7-79dc-43f1-ba4f-843840929621";

    private static final String BASE_URL = "https://content.guardianapis.com/search?";

    private static final int NEWS_LOADER_ID = 1;

    private TextView mEmptyView;

    private NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the ListView to set the arrayList
        ListView listView = findViewById(R.id.list);
        mEmptyView = findViewById(R.id.empty_text_view);
        listView.setEmptyView(mEmptyView);

        //create an arrayAdapter using the arrayList
        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());

        //set the adapter to ListView
        listView.setAdapter(mNewsAdapter);

        //set an onItemClickListener to each ListView item,
        //this sends a Intent to view the news in web browser
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the current item
                News currentNews = mNewsAdapter.getItem(position);

                //get the webUrl of currentNews and convert it into uri object
                //for web searching
                Uri uri = Uri.parse(currentNews.getWebUrl());

                //send a Intent for the uri
                Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(webIntent);
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyView.setText(R.string.no_internet);
        }
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String section = sharedPreferences.getString(
                getString(R.string.settings_section_key),
                getString(R.string.settings_section_default)
        );

        String pageSize = sharedPreferences.getString(
                getString(R.string.settings_page_key),
                getString(R.string.settings_pageSize_default)
        );

        //base uri
        Uri baseUri = Uri.parse(BASE_URL);

        //uri builder
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("section", section);
        uriBuilder.appendQueryParameter("show-fields", "thumbnail,trailText");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", pageSize);
        uriBuilder.appendQueryParameter("api-key", API_KEY);
        

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyView.setText(R.string.no_news_found);

        if (data != null && !data.isEmpty()) {
            mNewsAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNewsAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.news_settings) {
            Intent newsSettingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(newsSettingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
