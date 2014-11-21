package com.example.testtwitterclient.Activities;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.FlickrApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import org.scribe.builder.api.TwitterApi;

/*
 *  for communicating with a REST API.
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "szHJPyTdJwbw0bjYeMYtHdlEN";
	public static final String REST_CONSUMER_SECRET = "JPcbQtq9cibQ2ajHHExeRsaB4mqAROI3q3MQqDAs7GXlLG8oNv";
	public static final String REST_CALLBACK_URL = "oauth://twClient";

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

    public void getUserTimeLine (AsyncHttpResponseHandler handler){
        String url = getApiUrl("statuses/user_timeline.json?count=10&user_id=14230524");
        client.get(url, null, handler);
    }


}