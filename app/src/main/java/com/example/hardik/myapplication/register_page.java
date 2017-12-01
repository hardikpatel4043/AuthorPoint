package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class register_page extends AppCompatActivity {

    private EditText usname;
    private EditText pass;
    private Button b;
    DatabaseReference myRef;
    FirebaseDatabase database;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        usname = (EditText)findViewById(R.id.name);
        pass=(EditText)findViewById(R.id.pass);

        b=(Button)findViewById(R.id.sub);

         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("users");


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=usname.getText().toString();
                String s2=pass.getText().toString();
                Log.e("username",s1);
                Log.e("password",s2);
                if(usname.getText()!=null && pass.getText()!=null) {
                    myRef.child("username").setValue(s1);
                    myRef.child("password").setValue(s2);
                }


                startActivity(new Intent(register_page.this,display.class));

            }
        });


    }
}
