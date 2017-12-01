package com.example.hardik.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    private EditText e,password;
    private Button regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        e=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.pass);
        regist= (Button) findViewById(R.id.register);
        regist.setOnClickListener(this);
    }


    private void registerUser(){

        String email=e.getText().toString();
        String pass=password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(register.this,"Succesfully Registerd",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(register.this,"Registeration Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//end of registerUser() method
    public void onClick(View view){
          registerUser();
    }
}
