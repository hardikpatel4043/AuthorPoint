package com.example.hardik.myapplication;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AuthorInDetail extends AppCompatActivity {

    TextView name,email,phone;
    ImageView image;
    //Firebase
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_in_detail);


        name=findViewById(R.id.author_in_detail_name);
        email=findViewById(R.id.author_in_detail_email);
        phone=findViewById(R.id.author_in_detail_phone);
        image=findViewById(R.id.author_in_detail_profile_image);

        //firebase
        mRef= FirebaseDatabase.getInstance().getReference("author");

        String id=getIntent().getStringExtra("AuthorId");
        mRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              name.setText(dataSnapshot.child("name").getValue().toString());
              email.setText(dataSnapshot.child("email").getValue().toString());
              phone.setText(dataSnapshot.child("phone").getValue().toString());
                Glide.with(getApplicationContext()).load(dataSnapshot.child("image").getValue().toString())
                        .apply(RequestOptions.circleCropTransform()).into(image);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
