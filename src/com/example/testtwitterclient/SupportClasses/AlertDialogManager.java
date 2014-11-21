package com.example.testtwitterclient.SupportClasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by alexandr on 13.11.14.
 */
public class AlertDialogManager {

    public void showAlertDialog(Context context, String title, String message, boolean status){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

       alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {

           }
       });
        alertDialog.show();
    }
}
