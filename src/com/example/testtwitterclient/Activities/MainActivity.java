package com.example.testtwitterclient.Activities;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import com.example.testtwitterclient.R;
import com.example.testtwitterclient.SupportClasses.ConnectionDetector;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.codepath.oauth.OAuthLoginActivity;

public class MainActivity extends FragmentActivity{

	    private ConnectionDetector connectionDetector;
	
		private LoginButton loginFacebookBtn;
		private Button loginTwitterBtn;
		private Button showTweetsBtn;

		private TextView userName;

		private UiLifecycleHelper uiHelper;

		private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			connectionDetector = new ConnectionDetector(this);
			uiHelper = new UiLifecycleHelper(this, statusCallback);
			uiHelper.onCreate(savedInstanceState);
			setContentView(R.layout.login_activity_layout);
			
			loginFacebookBtn = (LoginButton) findViewById(R.id.fb_login_button);
			loginTwitterBtn = (Button) findViewById(R.id.btnTwitterLogin);
			showTweetsBtn = (Button) findViewById(R.id.btnShowLadyGagaTweets);
			
			
			if (!connectionDetector.isConnectionToInternet()) {
				Toast.makeText(this, "no working internet connection", Toast.LENGTH_LONG).show();
				showTweetsBtn.setEnabled(true);
				loginFacebookBtn.setEnabled(false);
				loginTwitterBtn.setEnabled(false);
				
				showTweetsBtn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						showTweets();
					}
				});
				
			} else {
			
				showTweetsBtn.setEnabled(false);
				
			int temp = getIntent().getIntExtra("flag", 0);
			if (temp > 0){
				loginTwitterBtn.setEnabled(false);
				showTweetsBtn.setEnabled(true);
				Toast.makeText(getApplicationContext(), "You are login Twitter successefully", Toast.LENGTH_LONG).show();
			}
			
			userName = (TextView) findViewById(R.id.user_name);
			
			
			loginFacebookBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
				@Override
				public void onUserInfoFetched(GraphUser user) {
					if (user != null) {
						userName.setText("Hello, " + user.getName());
					} else {
						userName.setText("You are not logged");
					}
				}
			});
			
			loginTwitterBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View view) {
					loginTwitter();
				}
			});
			showTweetsBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					showTweets();
				}
			});
		}
			}

		private Session.StatusCallback statusCallback = new Session.StatusCallback() {
			@Override
			public void call(Session session, SessionState state,
					Exception exception) {
				if (state.isOpened()) {
					//buttonsEnabled(showTweetsBtn,true);
					Log.d("", "Facebook session opened");
				} else if (state.isClosed()) {
					//buttonsEnabled(showTweetsBtn,false);
					Log.d("", "Facebook session closed");
				}
			}
		};

		public void buttonsEnabled(Button button, boolean isEnabled) {
			button.setEnabled(isEnabled);
		}

		public void loginTwitter() {
			Intent intent = new Intent(MainActivity.this, LoginTwitterActivity.class);
			startActivity(intent);
			this.finish();
		}

		public void showTweets() {
			Intent intent = new Intent(MainActivity.this, TimeLineActivity.class);
			startActivity(intent);
		}

		public boolean checkPermissions() {
			Session s = Session.getActiveSession();
			if (s != null) {
				return s.getPermissions().contains("publish_actions");
			} else
				return false;
		}

		public void requestPermissions() {
			Session s = Session.getActiveSession();
			if (s != null)
				s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
						this, PERMISSIONS));
		}

		@Override
		public void onResume() {
			super.onResume();
			uiHelper.onResume();
			//buttonsEnabled(showTweetsBtn,Session.getActiveSession().isOpened());
		}

		@Override
		public void onPause() {
			super.onPause();
			//uiHelper.onPause();
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			uiHelper.onDestroy();
		}

		@Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			super.onActivityResult(requestCode, resultCode, data);
			uiHelper.onActivityResult(requestCode, resultCode, data);
		}

		@Override
		public void onSaveInstanceState(Bundle savedState) {
			super.onSaveInstanceState(savedState);
			uiHelper.onSaveInstanceState(savedState);
		}
		
		 @Override
		    public void onBackPressed() {
		        finish();
		    }

	}


