package com.example.android.mynewsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsItemAdapter extends ArrayAdapter<NewsItem> {

    public NewsItemAdapter(@NonNull Context context, @NonNull List<NewsItem> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        NewsItem currentNewsItem = getItem(position);

        TextView textViewDate = convertView.findViewById(R.id.date_view);
        TextView textViewTime = convertView.findViewById(R.id.time_view);

        String publicationData = currentNewsItem.getPublicationDate();

        int offsetIndex = publicationData.toLowerCase().indexOf("t");
        textViewDate.setText(publicationData.substring(0, offsetIndex));
        textViewTime.setText(publicationData.substring(offsetIndex + 2, publicationData.length() - 1));

        TextView textViewNews = convertView.findViewById(R.id.news_view);
        textViewNews.setText(currentNewsItem.getHeadLine());

        TextView textViewSection = (TextView) convertView.findViewById(R.id.section_view);
        textViewSection.setText(currentNewsItem.getSectionName());

        return convertView;
    }

    private String formatDate(String date) {
        SimpleDateFormat dateFormatted = new SimpleDateFormat("LLL MM, yyyy");
        return dateFormatted.format(date);
    }

    private String formatTime(String time) {
        SimpleDateFormat timeFormatted = new SimpleDateFormat("h:mm a");
        return timeFormatted.format(time);
    }

}
