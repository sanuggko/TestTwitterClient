package com.example.testtwitterclient.Models;

import android.database.Cursor;
import com.example.testtwitterclient.SupportClasses.DBHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by alexandr on 17.11.14.
 */
public class Tweet extends BaseModel {

    private User user;

    public User getUser(){
        return user;
    }

    public String getBody (){
        return getString("text");
    }

    public long getId(){
        return getLong("id");
    }

    public String getCreateTime(){
        return getString("created_at");
    }


    public static Tweet fromJSON (JSONObject json){
        Tweet tweet = new Tweet();
        try {
           tweet.jsonObject = json;
            tweet.user = User.fromJSON(json.getJSONObject("user"));
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
        return tweet;
    }
    public static ArrayList<Tweet> fromJSON (JSONArray jsonArray){
        ArrayList<Tweet> tweetArrayList = new ArrayList<Tweet>(jsonArray.length());

        for (int i =0; i < jsonArray.length(); i++){
            JSONObject tweetJSON = null;
            try {
                tweetJSON = jsonArray.getJSONObject(i);
            }catch (JSONException e){
                e.printStackTrace();
                continue;
            }
            Tweet tweet = Tweet.fromJSON(tweetJSON);

            if (tweet != null){
                tweetArrayList.add(tweet);
            }
        }
        return tweetArrayList;
    }

}

