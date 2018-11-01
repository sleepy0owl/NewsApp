package com.example.sourav.newsapp;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class QueryUtils {

    //use sample json response to check
    private static final String jsonResponse = "";

    //private constructor so that no one can instantiate this class
    private QueryUtils(){

    }

    public static ArrayList<News> extractNews(){

        ArrayList<News> news = new ArrayList<>();
        try{
            JSONObject baseJsonObject = new JSONObject(jsonResponse);

            JSONArray newsJsonArray = baseJsonObject.getJSONArray("results");

            //loop over the newsJsonArray for the each news Json object
            for (int i = 0; i < newsJsonArray.length(); i++){
                JSONObject currentNews = newsJsonArray.getJSONObject(i);

                // get the require information from currentNews object
                String title = currentNews.getString("webTitle");
                String date = currentNews.getString("webPublicationDate");
                String webUrl = currentNews.getString("webUrl");
                String apiUrl = currentNews.getString("apiUrl");

                //create a news object using this information and add it to arrayList
                News  newsObject = new News(title, webUrl, apiUrl, date);
                news.add(newsObject);
            }
        }catch (JSONException e){
            Log.e("QueryUtils", "Problem in parsing JSON object", e);
        }
        return news;
    }
}
