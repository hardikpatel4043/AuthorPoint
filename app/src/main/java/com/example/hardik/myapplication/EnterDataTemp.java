package com.example.hardik.myapplication;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.hardik.myapplication.POJO.Book;
import com.example.hardik.myapplication.POJO.Reader;
import com.example.hardik.myapplication.POJO.Review;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EnterDataTemp extends AppCompatActivity {

    TextInputLayout layout1;
    Button submit;
    DatabaseReference database;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_data_temp);

        database=FirebaseDatabase.getInstance().getReference("events");
        user= FirebaseAuth.getInstance().getCurrentUser();

        layout1=findViewById(R.id.textInputLayout1);
        submit=findViewById(R.id.tem_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=user.getUid();

                String pushId=database.push().getKey();
                String parameter =layout1.getEditText().getText().toString();
                Review fakeReview=new Review("very great book");

                String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                Book enterData=new Book(timeStamp,89,"Everyone has a story ","book003","english","motivational",fakeReview,"jakdfjdlf","default");
                database.child(pushId).setValue(enterData);

            }
        });

    }
}
