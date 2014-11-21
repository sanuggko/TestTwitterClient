package com.example.testtwitterclient.SupportClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by alexandr on 13.11.14.
 */
public class ConnectionDetector {
   Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }
    public boolean isConnectionToInternet (){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(context.CONNECTIVITY_SERVICE);
       if (connectivityManager != null){
           NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
           if (info != null){
               for (int i = 0; i < info.length; i++){
                   if (info[i].getState() == NetworkInfo.State.CONNECTED){
                       return true;
                   }
               }
           }
       }
        return false;
    }
}
