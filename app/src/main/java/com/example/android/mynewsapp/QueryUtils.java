package com.example.android.mynewsapp;

import android.gesture.GestureUtils;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {

    private static final String appName = "MyNewsApp";

    private static URL createURL(String request_url) {
        URL url = null;
        try {
            url = new URL(request_url);
        }
        catch (MalformedURLException e) {
            Log.v(appName, "Error forming URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        HttpURLConnection httpURLConnection = null;
        String jsonResponse = null;
        InputStream inputStream = null;

        if (url == null) {
            return jsonResponse;
        }

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                jsonResponse = null;
                Log.v(appName, "Error fetching results");
            }
        }
        catch (IOException e) {
            Log.v(appName, "Error opening connection");
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder = new StringBuilder();

        if (inputStream != null) {
            inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            bufferedReader = new BufferedReader(inputStreamReader);
            try {
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
            }
            catch (IOException e) {
                Log.v(appName, "Error reading results", e);
            }
        }
        return stringBuilder.toString();
    }

    public static ArrayList<NewsItem> fetchNewsItems(String request_url) {

        URL url = createURL(request_url);

        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e) {
            Log.v(appName, "Error making request", e);
        }

        ArrayList<NewsItem> newsItems = new ArrayList<>();

        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject jsonObjectResponse = jsonObject.getJSONObject("response");
            JSONArray jsonArrayResults = jsonObjectResponse.getJSONArray("results");

            for (int i = 0; i < jsonArrayResults.length(); i++) {
                JSONObject news = jsonArrayResults.getJSONObject(i);
                String publication_date= news.getString("webPublicationDate");
                String title = news.getString("webTitle");
                String website = news.getString("webUrl");
                String section = news.getString("sectionName");

                newsItems.add(new NewsItem(publication_date, title, website, section));
            }
        }
        catch (JSONException e) {
            Log.v(appName, "Error preparing JSONObject", e);
        }

        return newsItems;
    }
}
