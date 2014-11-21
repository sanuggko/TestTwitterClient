package com.example.testtwitterclient.SupportClasses;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testtwitterclient.Activities.LoadPhotoActivity;
import com.example.testtwitterclient.Models.Tweet;
import com.example.testtwitterclient.Models.TweetDB;
import com.example.testtwitterclient.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * Created by alexandr on 17.11.14.
 */
public class TweetDBAdapter extends BaseAdapter{
    Context context;
    List<TweetDB> tweetDBs;

    public TweetDBAdapter(Context context, List<TweetDB> tweets) {
        this.context = context;
        this.tweetDBs = tweets;
    }

    @Override
    public int getCount() {
        return tweetDBs.size();
    }

    @Override
    public Object getItem(int position) {
        return tweetDBs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_item_layout, null);
        }
        TweetDB tweetDB = (TweetDB) getItem(position);


        ImageView userImage = (ImageView) view.findViewById(R.id.twProfileImageView);
        String url = (tweetDB.getProfileImageURL());
        System.out.print(url);
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        ImageLoader.getInstance().displayImage(tweetDB.getProfileImageURL(), userImage);

        TextView userName = (TextView) view.findViewById(R.id.twName);
        String fullName = tweetDB.getUserName() + " @ "+ tweetDB.getScreenName();
        userName.setText(fullName);

        TextView bodyText = (TextView) view.findViewById(R.id.twBody);
        bodyText.setText(tweetDB.getBodyText());

        TextView createTime = (TextView) view.findViewById(R.id.twCreateTime);
        createTime.setText(tweetDB.getCreateTime());
        
Button buttonShareButton = (Button) view.findViewById(R.id.buttonShare);
        
        buttonShareButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "No connection to Internet", Toast.LENGTH_LONG).show();
			}
		});
        
        return view;
    }
}
