package com.example.testtwitterclient.SupportClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexandr on 17.11.14.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tweets_database.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "tweets_table";

    public static final String UID = "_id";
    public static final String USER_NAME = "user_name";
    public static final String PROFILE_USER_NAME = "profile_user_name";
    public static final String CREATE_TIME = "create_at";
    public static final String TEXT_BODY = "text_body";
    public static final String PROFILE_IMAGE_URL = "profile_image_url";


    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + USER_NAME + " VARCHAR(255)," + PROFILE_USER_NAME + " VARCHAR(255)," + CREATE_TIME +
            " VARCHAR(255)," + TEXT_BODY + " VARCHAR(255)," + PROFILE_IMAGE_URL + " VARCHAR(255))" ;

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
