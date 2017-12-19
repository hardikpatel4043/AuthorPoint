package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_password extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseAuth notauth;
    EditText email;
    Button resetButoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        email=(EditText) findViewById(R.id.reset_email);
        resetButoon=findViewById(R.id.reset);
        auth=FirebaseAuth.getInstance();

        resetButoon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String emailId=email.getText().toString();

                auth.sendPasswordResetEmail(emailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Reset_password.this,"we have sent you mail",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(Reset_password.this,login_page.class));
                        }else{
                            Toast.makeText(Reset_password.this,"Error ",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });



    }


}
