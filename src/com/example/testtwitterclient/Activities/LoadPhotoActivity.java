package com.example.testtwitterclient.Activities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testtwitterclient.R;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
 
public class LoadPhotoActivity extends Activity {
	 
	    ImageView viewImage;
	    Button selectBtn;
	    Button backButton;
	    TextView txtPostedText;
	    ProgressBar spinner;
	    
	    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
		private String postedText;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.load_image_layout);
	        selectBtn=(Button)findViewById(R.id.btnSelPhoto);
	        viewImage=(ImageView)findViewById(R.id.viewImage);
	        backButton = (Button) findViewById(R.id.backBtn);
	        txtPostedText = (TextView) findViewById(R.id.txtPostedText);
	        spinner = (ProgressBar) findViewById(R.id.spinner);
	        spinner.setVisibility(View.GONE);
	        
	        postedText = getIntent().getStringExtra("PostedText");
	        
	        txtPostedText.setText(postedText);
	        
	        backButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onBackPressed();
				}
			});
	        
	        selectBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                selectImage();
	            }
	        });
	    }
	 	 
	      private void selectImage() {
	 
	        final CharSequence[] options = {"Choose from Gallery","Cancel" };
	 
	        AlertDialog.Builder builder = new AlertDialog.Builder(LoadPhotoActivity.this);
	        builder.setTitle("Add Photo!");
	        builder.setItems(options, new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {
	            if (options[item].equals("Choose from Gallery"))
	                {
	                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	                    startActivityForResult(intent, 2);
	 
	                }
	                else if (options[item].equals("Cancel")) {
	                    dialog.dismiss();
	                }
	            }
	        });
	        builder.show();
	    }
	 
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (resultCode == RESULT_OK) {
	            if (requestCode == 2) {
	 
	                Uri selectedImage = data.getData();
	                String[] filePath = { MediaStore.Images.Media.DATA };
	                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
	                c.moveToFirst();
	                int columnIndex = c.getColumnIndex(filePath[0]);
	                String picturePath = c.getString(columnIndex);
	                c.close();
	                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
	                viewImage.setImageBitmap(thumbnail);
	                spinner.setVisibility(View.VISIBLE);
	                selectBtn.setVisibility(View.GONE);
	                postImage(picturePath);
	            }
	        }
	    }  
	    public void postImage(String picturePath) {
			if (checkPermissions()) {
				Bitmap img = BitmapFactory.decodeFile(picturePath);
				Request uploadRequest = Request.newUploadPhotoRequest(
						Session.getActiveSession(), img, new Request.Callback() {
							@Override
							public void onCompleted(Response response) {
								spinner.setVisibility(View.GONE);
								Toast.makeText(LoadPhotoActivity.this,
										"Photo uploaded successfully",
										Toast.LENGTH_LONG).show();
										Intent intent = new Intent(LoadPhotoActivity.this, TimeLineActivity.class);
										startActivity(intent);
										finish();
							}
						});
				Bundle param = uploadRequest.getParameters();
				param.putString("message", postedText);
				uploadRequest.executeAsync();
				Toast.makeText(LoadPhotoActivity.this,
						"The post loading to your facebook wall...",
						Toast.LENGTH_LONG).show();				
			} else {
				requestPermissions();
			}
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

	}

