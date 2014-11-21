package com.example.testtwitterclient.Activities;

/**
 * Created by alexandr on 16.11.14.
 */

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.testtwitterclient.R;
import com.example.testtwitterclient.Models.Tweet;
import com.example.testtwitterclient.Models.TweetDB;
import com.example.testtwitterclient.SupportClasses.AlertDialogManager;
import com.example.testtwitterclient.SupportClasses.ConnectionDetector;
import com.example.testtwitterclient.SupportClasses.DBHelper;
import com.example.testtwitterclient.SupportClasses.TweetAdapter;
import com.example.testtwitterclient.SupportClasses.TweetDBAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimeLineActivity extends Activity {

    ConnectionDetector connectionDetector;
    Context context;
    ProgressBar spinner;

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_layout);
        spinner = (ProgressBar) findViewById(R.id.spinnerTimeLine);
        AlertDialogManager alert = new AlertDialogManager();
        connectionDetector = new ConnectionDetector(TimeLineActivity.this);

        if (!connectionDetector.isConnectionToInternet()) {
            alert.showAlertDialog(TimeLineActivity.this, "Internet Connection Error",
                    "Please connect to working Internet connection," +
                            " your tweets will load from dataBase", false);

               loadDataFromDB();
               spinner.setVisibility(View.GONE);

        } else {

              loadDataFromJSONResponce();
              
        }
        final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeView.setColorScheme(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_green_dark);
        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        TextView noConnectionMsg = (TextView) findViewById(R.id.txtNoConectionMsg);
            @Override
            public void onRefresh() {
                swipeView.setRefreshing(true);
                ( new Handler()).postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        swipeView.setRefreshing(false);
                        if (!connectionDetector.isConnectionToInternet()){
                            noConnectionMsg.setText("No connection to Internet");
                            loadDataFromDB();
                            spinner.setVisibility(View.GONE);

                        }

                        else {
                            noConnectionMsg.setText("");
                            loadDataFromJSONResponce();
                            spinner.setVisibility(View.GONE);
                        }
                      }
                }, 3000);
            }
        });
    }

    public void initTweetDataBace(SQLiteDatabase sqLiteDB, List<Tweet> tweets, DBHelper helper){
        ContentValues values = new ContentValues();
        sqLiteDB.delete(DBHelper.TABLE_NAME, null, null);

        for (Tweet tweet : tweets){
            values.put(DBHelper.USER_NAME, tweet.getUser().getName());
            values.put(DBHelper.PROFILE_USER_NAME, tweet.getUser().getScreenName());
            values.put(DBHelper.CREATE_TIME, tweet.getCreateTime());
            values.put(DBHelper.TEXT_BODY, tweet.getBody());
            values.put(DBHelper.PROFILE_IMAGE_URL, tweet.getUser().getProfileImageURL());

            sqLiteDB.insert(DBHelper.TABLE_NAME, null, values);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnReload:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
        }

        return true;
    }
    @Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	super.onDestroy();
    }
    
    public void loadDataFromDB (){
        DBHelper dbHelper = new DBHelper(TimeLineActivity.this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = sqLiteDatabase.query(DBHelper.TABLE_NAME, null,null, null, null, null,null);
        ArrayList<TweetDB> listTweetsDB = TweetDB.fromDB(cursor);

        ListView listView = (ListView) findViewById(R.id.timeLineListView);
        TweetDBAdapter adapterDB = new TweetDBAdapter(TimeLineActivity.this, listTweetsDB);

        listView.setAdapter(adapterDB);

        dbHelper.close();
        sqLiteDatabase.close();
    }

    public void loadDataFromJSONResponce (){
        TwitterClientApp.getRestClient().getUserTimeLine(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(JSONArray jsonTweets) {
                ArrayList<Tweet> tweetsList = Tweet.fromJSON(jsonTweets);

                ListView listView = (ListView) findViewById(R.id.timeLineListView);
                TweetAdapter adapter = new TweetAdapter(getBaseContext(), tweetsList);

                listView.setAdapter(adapter);

                DBHelper dbHelper = new DBHelper(TimeLineActivity.this);
                SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

                initTweetDataBace(sqLiteDatabase, tweetsList, dbHelper);

                dbHelper.close();
                sqLiteDatabase.close();
                spinner.setVisibility(View.GONE);
            }
        });
    }
     
}

