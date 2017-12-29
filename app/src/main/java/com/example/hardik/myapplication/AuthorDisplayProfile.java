package com.example.hardik.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class AuthorDisplayProfile extends AppCompatActivity {

    TextView name,email,phone;
    ImageView image;
    Button mFriendRequestButton;
    //Firebase
    DatabaseReference mRef;

    DatabaseReference mFriendRequestDatabase;

    DatabaseReference mFriendDatabase;

    FirebaseUser mCurrentUser;
    String mCurrent_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_display_profile);

        name=findViewById(R.id.author_in_detail_name);
        email=findViewById(R.id.author_in_detail_email);
        phone=findViewById(R.id.author_in_detail_phone);
        image=findViewById(R.id.author_in_detail_profile_image);
        mFriendRequestButton=findViewById(R.id.author_display_friend_button);

        //firebase database
        mRef= FirebaseDatabase.getInstance().getReference("author");
        mFriendRequestDatabase=FirebaseDatabase.getInstance().getReference("friends_req");
        mFriendDatabase=FirebaseDatabase.getInstance().getReference("friends");

        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();

        final String viewUserId=getIntent().getStringExtra("AuthorId");

        mCurrent_state="not_friends";

        //---------------------Display author Data-----------
        mRef.child(viewUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name.setText(dataSnapshot.child("name").getValue().toString());
                email.setText(dataSnapshot.child("email").getValue().toString());
                phone.setText(dataSnapshot.child("phone").getValue().toString());
                Glide.with(getApplicationContext()).load(dataSnapshot.child("image").getValue().toString())
                        .apply(RequestOptions.circleCropTransform()).into(image);

                //----------------REQUEST RECEIVED CHECK FOR REQUEST------------------------
                mFriendRequestDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild(viewUserId)){
                            String req_type=dataSnapshot.child(viewUserId).child("request_type").getValue().toString();

                            if(req_type.equals("received")){

                                mCurrent_state="req_received";
                                mFriendRequestButton.setText("Accept Friend Request");
                            }else if(req_type.equals("sent")){
                                mCurrent_state="req_sent";
                                mFriendRequestButton.setText("Cancel Friend Request");
                            }

                        }else {
                            mFriendDatabase.child(mCurrentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.hasChild(viewUserId)){
                                        mCurrent_state="friends";
                                        mFriendRequestButton.setText("UnFriend");
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //-------------------
        mFriendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFriendRequestButton.setEnabled(false);

                //----------------NOT FRIENDS STATE--------------
                if(mCurrent_state.equals("not_friends")){

                    mFriendRequestDatabase.child(mCurrentUser.getUid()).child(viewUserId).child("request_type").setValue("sent")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()){

                                    mFriendRequestDatabase.child(viewUserId).child(mCurrentUser.getUid()).child("request_type").setValue("received")
                                             .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                 @Override
                                                 public void onSuccess(Void aVoid) {

                                                     mCurrent_state="req_sent";
                                                     mFriendRequestButton.setText("Cancel Friend Request");

                                                 }
                                             });
                                 }else{
                                     Toast.makeText(AuthorDisplayProfile.this,"Failed To send Friend Request",Toast.LENGTH_SHORT).show();
                                 }
                                    mFriendRequestButton.setEnabled(true);
                                }
                            });
                }//End of if

                //----------------CANCEL REQUEST STATE----------
                if(mCurrent_state.equals("req_sent")){

                    mFriendRequestDatabase.child(mCurrentUser.getUid()).child(viewUserId).removeValue()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    mFriendRequestDatabase.child(viewUserId).child(mCurrentUser.getUid()).removeValue()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                    mFriendRequestButton.setEnabled(true);
                                                    mCurrent_state="not_friends";
                                                    mFriendRequestButton.setText("send Friend Request");

                                                }
                                            });
                                }
                            });
                }//End of if

                //------------------REQUEST  RECEIVED STATE---------
                if(mCurrent_state.equals("req_received")){

                    final String currentDate= DateFormat.getDateInstance().format(new Date());
                    mFriendDatabase.child(mCurrentUser.getUid()).child(viewUserId).setValue(currentDate)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        mFriendDatabase.child(viewUserId).child(mCurrentUser.getUid()).setValue(currentDate)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){

                                                            mFriendRequestDatabase.child(mCurrentUser.getUid()).child(viewUserId).removeValue()
                                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                        @Override
                                                                        public void onSuccess(Void aVoid) {

                                                                            mFriendRequestDatabase.child(viewUserId).child(mCurrentUser.getUid()).removeValue()
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if(task.isSuccessful()){

                                                                                                mCurrent_state="friends";
                                                                                                mFriendRequestButton.setText("UnFriend");

                                                                                            }
                                                                                            mFriendRequestButton.setEnabled(true);
                                                                                        }
                                                                                    });
                                                                        }
                                                                    });

                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }//End of if

                //---------------------UNFRIEND STATE -------------------------------
                if(mCurrent_state.equals("friends")){
                    mFriendDatabase.child(mCurrentUser.getUid()).child(viewUserId).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                mFriendDatabase.child(viewUserId).child(mCurrentUser.getUid()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    mCurrent_state="not_friends";
                                                    mFriendRequestButton.setText("send friends request");
                                                }

                                                mFriendRequestButton.setEnabled(true);
                                            }

                                        });
                            }
                        }
                    });

                }



            }//End of onClick() method
        });



    }//End of onCreate() method
}//End of AuthorDisplayProfile
