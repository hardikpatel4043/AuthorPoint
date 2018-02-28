package com.example.hardik.myapplication.pojo;

import android.app.Application;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Hardik on 12/29/2017.
 */

public class OfflineAuthorPoint extends Application {

    private DatabaseReference mUserReference;
    private FirebaseUser mCurrentUser;

    @Override
    public void onCreate(){
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        mUserReference=FirebaseDatabase.getInstance().getReference("author");

        if(mCurrentUser!=null){

            mUserReference.child(mCurrentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot!=null) {
                        mUserReference.child(mCurrentUser.getUid()).child("online").onDisconnect().setValue("offline");
                        //mUserReference.child(mCurrentUser.getUid()).child("online").setValue(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

}
