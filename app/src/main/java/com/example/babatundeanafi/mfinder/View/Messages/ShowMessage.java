package com.example.babatundeanafi.mfinder.View.Messages;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

/**
 * Created by babatundeanafi on 19/11/2016.
 */




//
public class ShowMessage {



    public void showToast(Context context, String message){



        Toast.makeText(context, message , Toast.LENGTH_LONG).show();

    }

    //Method to show Dialog
    //Reference:http://www.journaldev.com/9463/android-alertdialog
    public void showDialog(AlertDialog.Builder builder, String tittle, String message) {

        builder.setTitle(tittle);
        builder.setMessage(message);
        builder.show();
    }

    //Method to show Snackbar
    //Reference:http://www.androidhive.info/2015/09/android-material-design-snackbar-example/
    public void showSnackbar(View v, String mMessage ){
        Snackbar.make(v, mMessage, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }


}

