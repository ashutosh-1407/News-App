package com.example.android.mynewsapp;

public class NewsItem {

    String mPublicationDate = "";
    String mHeadLine = "";
    String mUrl = "";
    String mSectionName = "";

    public NewsItem(String publicationDate, String headLine, String url, String sectionName) {
        mPublicationDate = publicationDate;
        mHeadLine = headLine;
        mUrl = url;
        mSectionName = sectionName;
    }

    public String getPublicationDate() { return mPublicationDate; }

    public String getHeadLine() { return mHeadLine; }

    public String getUrl() { return mUrl; }

    public String getSectionName() { return mSectionName; }

}
