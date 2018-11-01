package com.example.sourav.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String jsonResponse = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get the arrayList of News using QueryUtils
        ArrayList<News> newsList = (ArrayList<News>) QueryUtils.extractNews(jsonResponse);

        //get the ListView to set the arrayList
        ListView listView = findViewById(R.id.list);

        //create an arrayAdapter using the arrayList
        final NewsAdapter newsAdapter = new NewsAdapter(this, newsList);

        //set the adapter to ListView
        listView.setAdapter(newsAdapter);

        //set an onItemClickListener to each ListView item,
        //this sends a Intent to view the news in web browser
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get the current item
                News currentNews = newsAdapter.getItem(position);

                //get the webUrl of currentNews and convert it into uri object
                //for web searching
                Uri uri = Uri.parse(currentNews.getWebUrl());

                //send a Intent for the uri
                Intent webIntent = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(webIntent);
            }
        });
    }
}
