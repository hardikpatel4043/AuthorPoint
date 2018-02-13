package com.example.hardik.myapplication.POJO;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Hardik on 2/12/2018.
 */

public class CheckNetwork {

    public static boolean isInternetAvailable(Context context)
    {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null)
        {
            Log.e(">>>>>>>","no internet connection");
            return false;
        }
        else
        {
            if(info.isConnected())
            {
                Log.e(">>>>"," internet connection available...");
                return true;
            }
            else
            {
                Log.e(">>>>>>"," internet connection");
                return true;
            }
        }
    }
}