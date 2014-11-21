package com.example.testtwitterclient.SupportClasses;

import java.util.List;

import com.example.testtwitterclient.Activities.LoadPhotoActivity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MyOnClickListener implements OnClickListener{
	Context context;
	String postedText;
	
	public MyOnClickListener (Context context, String postedText){
		this.context = context;
		this.postedText = postedText;
	}
	
	@Override
	public void onClick(View v) {
		
		Intent intent = new Intent(context, LoadPhotoActivity.class);
		intent.putExtra("PostedText", postedText);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}


