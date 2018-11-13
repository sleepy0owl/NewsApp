package com.example.sourav.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }
        News currentNews = getItem(position);

        if (currentNews != null) {

            ImageView thumbNail = listItemView.findViewById(R.id.thumbnail);
            if (currentNews.getThumbnailUrl() != null) {
                thumbNail.setVisibility(View.VISIBLE);
                //use picasso to fetch the thumbnail using the url
                Picasso.get().load(currentNews.getThumbnailUrl()).into(thumbNail);
            } else {
                thumbNail.setVisibility(View.GONE);
            }
            //Title of the article
            TextView titleTextView = listItemView.findViewById(R.id.Title);
            titleTextView.setText(currentNews.getTitle());
            //trail Text of the article
            TextView trailText = listItemView.findViewById(R.id.trailText);
            trailText.setText(currentNews.getTrailText());
            //published date of the article
            TextView dateTextView = listItemView.findViewById(R.id.published_date);
            dateTextView.setText(currentNews.getPublishedDate());
            //section name textView
            TextView sectionName = listItemView.findViewById(R.id.section_name);
            sectionName.setText(currentNews.getSectionName());
            //author name textView
            TextView authorName = listItemView.findViewById(R.id.authorName);
            if (currentNews.getAuthorName() != null) {
                authorName.setText(currentNews.getAuthorName());
            } else authorName.setText(" ");
        }

        return listItemView;
    }
}
