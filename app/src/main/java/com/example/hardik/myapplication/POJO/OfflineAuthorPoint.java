package com.example.hardik.myapplication.POJO;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Hardik on 12/29/2017.
 */

public class OfflineAuthorPoint extends Application {


    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }



}
