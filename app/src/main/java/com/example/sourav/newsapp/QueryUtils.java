package com.example.sourav.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    //tag for error message
    private static final String TAG = QueryUtils.class.getSimpleName();
    //use sample json response to check
    //private static final String jsonResponse = "";

    //private constructor so that no one can instantiate this class
    private QueryUtils() {

    }

    /**
     * fetch news method fetches the news objects
     *
     * @return List of News Objects
     */
    public static List<News> fetchNews(String requestUrl) {
        //make the string url a url object to request over http
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
            Log.v(TAG, jsonResponse);
        } catch (IOException e) {
            Log.e(TAG, "Problem in Http request.", e);
        }

        List<News> newsList;
        newsList = extractNews(jsonResponse);
        return newsList;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        //if the url object is null return
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            //create http connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.v(TAG, "connected ...");
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readInputStream(inputStream);
                Log.v(TAG, "reading InputStream");
            } else {
                Log.e(TAG, "Error in connection: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "Error in retrieving JSON response.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * creates a url object from sting url
     *
     * @return url
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            Log.v(TAG, "URL is created");
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error building the url", e);
        }
        return url;
    }

    private static String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder outputString = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                outputString.append(line);
                line = bufferedReader.readLine();
            }
        }
        return outputString.toString();
    }

    public static List<News> extractNews(String jsonResponse) {

        //if the json response is null return null
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<News> news = new ArrayList<>();
        try {
            JSONObject baseJsonObject = new JSONObject(jsonResponse);

            JSONObject firstObject = baseJsonObject.getJSONObject("response");

            JSONArray newsJsonArray = firstObject.getJSONArray("results");

            //loop over the newsJsonArray for the each news Json object
            for (int i = 0; i < newsJsonArray.length(); i++) {
                JSONObject currentNews = newsJsonArray.getJSONObject(i);

                //get fields array
                JSONObject fieldsObject = currentNews.getJSONObject("fields");
                // get the require information from currentNews object
                String sectionName = currentNews.getString("sectionName");
                String title = currentNews.getString("webTitle");
                String date = currentNews.getString("webPublicationDate");
                String webUrl = currentNews.getString("webUrl");
                String thumbnailUrl = fieldsObject.getString("thumbnail");
                String trailText = fieldsObject.getString("trailText");
                JSONArray tagsArray = currentNews.getJSONArray("tags");
                String authorName =  "";
                for (int j = 0; j < tagsArray.length(); j++){
                    JSONObject contributorObject = tagsArray.getJSONObject(j);
                    authorName += contributorObject.getString("webTitle") + " ";
                }

                //create a news object using this information and add it to arrayList
                News newsObject = new News(title, webUrl, thumbnailUrl, trailText, date, sectionName, authorName);
                news.add(newsObject);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem in parsing JSON object", e);
        }
        return news;
    }
}
