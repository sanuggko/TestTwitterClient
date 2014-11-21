package com.example.testtwitterclient.Models;

import android.database.Cursor;
import com.example.testtwitterclient.SupportClasses.DBHelper;

import java.util.ArrayList;

/**
 * Created by alexandr on 17.11.14.
 */
public class TweetDB {
    private String userName;
    private String screenName;
    private String profileImageURL;
    private String BodyText;
    private String CreateTime;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getBodyText() {
        return BodyText;
    }

    public void setBodyText(String bodyText) {
        BodyText = bodyText;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public static ArrayList<TweetDB> fromDB (Cursor cursor){

        ArrayList<TweetDB> tweetArrayList = new ArrayList<TweetDB>(cursor.getColumnCount());

        if (cursor.moveToFirst()){
            int idColIndex = cursor.getColumnIndex(DBHelper.UID);
            int userNameColIndex = cursor.getColumnIndex(DBHelper.USER_NAME);
            int profileNameColIndex = cursor.getColumnIndex(DBHelper.PROFILE_USER_NAME);
            int createTimeColIndex = cursor.getColumnIndex(DBHelper.CREATE_TIME);
            int textBodyColIndex = cursor.getColumnIndex(DBHelper.TEXT_BODY);
            int profileImageURLColIndex = cursor.getColumnIndex(DBHelper.PROFILE_IMAGE_URL);

            do {
                TweetDB tweetDB = new TweetDB();
                tweetDB.setUserName(cursor.getString(userNameColIndex));
                tweetDB.setScreenName(cursor.getString(profileNameColIndex));
                tweetDB.setCreateTime(cursor.getString(createTimeColIndex));
                tweetDB.setBodyText(cursor.getString(textBodyColIndex));
                tweetDB.setProfileImageURL(cursor.getString(profileImageURLColIndex));

                tweetArrayList.add(tweetDB);

            } while (cursor.moveToNext());
        }
        return tweetArrayList;
    }
}
