package com.example.testtwitterclient.SupportClasses;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testtwitterclient.Activities.LoadPhotoActivity;
import com.example.testtwitterclient.Activities.TimeLineActivity;
import com.example.testtwitterclient.Models.Tweet;
import com.example.testtwitterclient.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandr on 17.11.14.
 */
public class TweetAdapter extends BaseAdapter {
    Context context;
    List<Tweet> tweets;
    
   
    public TweetAdapter(Context context, List<Tweet> tweets ) {
        this.context = context;
        this.tweets = tweets;
    }
    
    @Override
    public int getCount() {
        return tweets.size();
    }

    @Override
    public Object getItem(int position) {
        return tweets.get(position);
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
        List<String> postedTexts = new ArrayList<String>();
        Tweet tweet = (Tweet) getItem(position);


        ImageView userImage = (ImageView) view.findViewById(R.id.twProfileImageView);
        String url = (tweet.getUser().getProfileImageURL());

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        ImageLoader.getInstance().displayImage(url, userImage);

        TextView userName = (TextView) view.findViewById(R.id.twName);
        String fullName = tweet.getUser().getName() + " @ "+ tweet.getUser().getScreenName();
        userName.setText(fullName);

        TextView bodyText = (TextView) view.findViewById(R.id.twBody);
        String bodyTextStr = Html.fromHtml(tweet.getBody()).toString();
        bodyText.setText(Html.fromHtml(tweet.getBody()));

        TextView createTime = (TextView) view.findViewById(R.id.twCreateTime);
        createTime.setText(tweet.getCreateTime());
        
        String postedText = "Create at " + tweet.getCreateTime() + "\n by " + fullName + "\n " + bodyTextStr;
        Button buttonShareButton = (Button) view.findViewById(R.id.buttonShare);
        
        postedTexts.add(new String(postedText));
        
        buttonShareButton.setOnClickListener(new MyOnClickListener(context, postedText));
        return view;
    }
    

}
