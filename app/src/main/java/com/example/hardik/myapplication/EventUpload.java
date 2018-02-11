package com.example.hardik.myapplication;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventUpload extends Fragment {

    private TextInputLayout eventName,eventDate,eventTime,eventPlace,eventDescription;
    private Button eventSubmitButton;
    private DatabaseReference rootRef;

    public EventUpload() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView= inflater.inflate(R.layout.event_upload, container, false);

       rootRef=FirebaseDatabase.getInstance().getReference();

       eventName=rootView.findViewById(R.id.event_name);
       eventPlace=rootView.findViewById(R.id.event_place);
       eventTime=rootView.findViewById(R.id.event_time);
       eventDate=rootView.findViewById(R.id.event_date);
       eventDescription=rootView.findViewById(R.id.event_description);

       eventSubmitButton=rootView.findViewById(R.id.event_upload_submit);

       eventSubmitButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               String pushKey=rootRef.push().getKey();

               HashMap uploadData=new HashMap();
               uploadData.put("name",eventName.getEditText().getText().toString());
               uploadData.put("place",eventPlace.getEditText().getText().toString());
               uploadData.put("time",eventTime.getEditText().getText().toString());
               uploadData.put("description",eventDescription.getEditText().getText().toString());
               uploadData.put("date",eventDate.getEditText().getText().toString());

               rootRef.child("events").child(pushKey).setValue(uploadData);

               Toast.makeText(getActivity(), "Succefully Event created", Toast.LENGTH_SHORT).show();

           }
       });


       return rootView;
    }

}
