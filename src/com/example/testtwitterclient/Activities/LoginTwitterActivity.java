package com.example.testtwitterclient.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import android.widget.Toast;
import com.codepath.oauth.OAuthLoginActivity;
import com.example.testtwitterclient.R;
import com.example.testtwitterclient.SupportClasses.ConnectionDetector;

public class LoginTwitterActivity extends OAuthLoginActivity<TwitterClient> {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter_activity_login);
    }

	@Override
	public void onLoginSuccess() {
		Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("flag", 1);
        startActivity(intent);
        finish();
	}

	@Override
	public void onLoginFailure(Exception e) {
		e.printStackTrace();
	}


	public void loginToRest(View view) {
		getClient().connect();
	}


}
