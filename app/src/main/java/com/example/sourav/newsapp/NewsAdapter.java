package com.example.sourav.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> news){
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }
        News currentNews = getItem(position);

        TextView titleTextView = listItemView.findViewById(R.id.Title);
        titleTextView.setText(currentNews.getTitle());

        TextView dateTextView = listItemView.findViewById(R.id.published_date);
        dateTextView.setText(currentNews.getPublishedDate());

        return listItemView;
    }
}
