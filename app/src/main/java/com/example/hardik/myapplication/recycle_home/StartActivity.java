package com.example.hardik.myapplication.recycle_home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.hardik.myapplication.AuthorRegisteration;
import com.example.hardik.myapplication.R;
import com.example.hardik.myapplication.SelectUserLogin;
import com.example.hardik.myapplication.ReaderRegister;

public class StartActivity extends AppCompatActivity {

    Button author_button;
    Button reader_button;
    LinearLayout layoutInvisibel;
    LinearLayout layoutvisibel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);


        author_button=findViewById(R.id.author_register);
        reader_button=findViewById(R.id.reader_register);

        layoutvisibel=findViewById(R.id.make_visible_layout);
        layoutInvisibel=findViewById(R.id.first_layout_make_invisible);
    }

    public void sendMessage(View view){
        Intent intent =new Intent(this,SelectUserLogin.class);
        startActivity(intent);
    }

    //Click on ReaderRegister button
    public void gotoRegister(View view){

        layoutInvisibel.setVisibility(View.GONE);
        layoutvisibel.setVisibility(View.VISIBLE);

        reader_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,ReaderRegister.class);
                startActivity(intent);
            }
        });

        author_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent authorRegister=new Intent(StartActivity.this, AuthorRegisteration.class);
                startActivity(authorRegister);
            }
        });

    }//end of gotoRegister method


}
