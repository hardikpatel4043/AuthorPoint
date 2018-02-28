package com.example.hardik.myapplication.pojo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Hardik on 2/12/2018.
 */

public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        if(checkInternet(context))
        {
            Toast.makeText(context, "Network is not Available ",Toast.LENGTH_LONG).show();
        }

    }

    boolean checkInternet(Context context) {
        CheckNetwork serviceManager = new CheckNetwork();
        if (serviceManager.isInternetAvailable(context)) {
            return true;
        } else {
            return false;
        }
    }

}

