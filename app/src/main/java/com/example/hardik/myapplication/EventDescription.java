package com.example.hardik.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EventDescription extends AppCompatActivity {

    DatabaseReference mRoot;

    TextView name,place,date,time,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_description);

        mRoot= FirebaseDatabase.getInstance().getReference();

        name=findViewById(R.id.event_name);
        place=findViewById(R.id.event_place);
        date=findViewById(R.id.event_date);
        time=findViewById(R.id.event_time);
        description=findViewById(R.id.event_about_description_);

        String event=getIntent().getStringExtra("Event");

        mRoot.child("events").child(event).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name.setText("Event Name : "+dataSnapshot.child("name").getValue().toString());
                place.setText("Event place : "+dataSnapshot.child("place").getValue().toString());
                description.setText("Event Description : "+dataSnapshot.child("description").getValue().toString());
                time.setText("Event Time : "+dataSnapshot.child("time").getValue().toString());
                date.setText("Evemt Date : "+dataSnapshot.child("date").getValue().toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
