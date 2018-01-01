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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthorDisplayProfile extends AppCompatActivity {

    TextView name,email,phone;
    ImageView image;
    Button mFriendRequestButton,mDeclineRequetButton;
    //Firebase
    DatabaseReference mRef;

    DatabaseReference mFriendRequestDatabase;

    DatabaseReference mFriendDatabase;
    DatabaseReference mNotificationDatabase;

    DatabaseReference mRootRef;
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
        mDeclineRequetButton=findViewById(R.id.author_display_decline_btn);

        //firebase database
        mRootRef=FirebaseDatabase.getInstance().getReference();
        mRef= FirebaseDatabase.getInstance().getReference("author");
        mFriendRequestDatabase=FirebaseDatabase.getInstance().getReference("friends_req");
        mFriendDatabase=FirebaseDatabase.getInstance().getReference("friends");
        mNotificationDatabase=FirebaseDatabase.getInstance().getReference("notifications");
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
                                mDeclineRequetButton.setVisibility(View.VISIBLE);

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

        //-------------------Button Click Listener-----------
        mFriendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                mFriendRequestButton.setEnabled(false);

                //---------------------------NOT FRIENDS STATE--------------------------
                if(mCurrent_state.equals("not_friends")){

                    DatabaseReference notificationRef=mRootRef.child("notifications").child(viewUserId).push();
                    String pushId=notificationRef.getKey();

                    HashMap<String,String> notificationData=new HashMap<>();
                    notificationData.put("from",mCurrentUser.getUid());
                    notificationData.put("type","request");

                    Map requestMap=new HashMap<>();
                    requestMap.put("friends_req/"+mCurrentUser.getUid()+"/"+viewUserId+"/"+"request_type","sent");
                    requestMap.put("friends_req/"+viewUserId+"/"+mCurrentUser.getUid()+"/"+"request_type","received");
                    requestMap.put("notifications/"+viewUserId+"/"+pushId, notificationData);

                    mRootRef.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if(databaseError!=null){
                                Toast.makeText(AuthorDisplayProfile.this,"There was some error in sent request",Toast.LENGTH_SHORT).show();
                            }else{
                                mCurrent_state="req_sent";
                                mFriendRequestButton.setText("Cancel Friend Request");
                            }

                            mFriendRequestButton.setEnabled(true);
                        }
                    });
                }//End of if


                //---------------------------CANCEL REQUEST STATE--------------------
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

                //-------------------------REQUEST  RECEIVED STATE--------------------
                if(mCurrent_state.equals("req_received")){

                    final String currentDate=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

                    Map requestReceiveMap=new HashMap();
                    requestReceiveMap.put("friends/"+ mCurrentUser.getUid()+ "/"+ viewUserId ,currentDate);
                    requestReceiveMap.put("friends/"+ viewUserId+ "/"+ mCurrentUser.getUid() ,currentDate);

                    requestReceiveMap.put("friends_req/"+ mCurrentUser.getUid()+ "/"+ viewUserId ,null);
                    requestReceiveMap.put("friends_req/"+ viewUserId+ "/"+ mCurrentUser.getUid(),null);

                    mRootRef.updateChildren(requestReceiveMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError==null){
                                mCurrent_state="friends";
                                mFriendRequestButton.setText("UnFriend");
                                mDeclineRequetButton.setVisibility(View.INVISIBLE);
                            }else{
                                Toast.makeText(AuthorDisplayProfile.this,""+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                            mFriendRequestButton.setEnabled(true);
                        }
                    });

                }//End of if

                //---------------------------UNFRIEND STATE -------------------------------
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
        });//End of mFriendRequestButton method

        //---------------------------Decline Friend Request-------------------------
        mDeclineRequetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                                                mDeclineRequetButton.setVisibility(View.INVISIBLE);
                                            }
                                        });
                            }
                        });
            }
        });//End of mDeclineRequest method


        //---------------
    }//End of onCreate() method
}//End of AuthorDisplayProfile
