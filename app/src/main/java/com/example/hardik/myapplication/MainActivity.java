package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void sendMessage(View view){
          Intent intent =new Intent(this,login_page.class);
          startActivity(intent);

    }

    public void gotoRegister(View view){
        Intent intent=new Intent(this,register.class);
        startActivity(intent);
    }
    public void next(){


    }
}
