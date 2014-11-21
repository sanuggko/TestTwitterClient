package com.example.testtwitterclient.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by alexandr on 16.11.14.
 */
public class User extends BaseModel {


    public String getName(){
        return getString("name");
    }

    public long getId(){
        return getLong("id");
    }

    public String getScreenName(){
        return getString("screen_name");
    }

    public String getProfileImageURL(){
        return getString("profile_image_url");
    }

    public int getNumTweets(){
        return getInt("statuses_count");
    }

    public int getFriendsCount(){
        return getInt("friends_count");
    }

    public static User fromJSON (JSONObject json){
        User user = new User();
        try{
           user.jsonObject = json;
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
