package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference ref;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;
    private EditText e,password,name,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        ref=FirebaseDatabase.getInstance().getReference("Users");
        
        name=(EditText) findViewById(R.id.name);
        e=(EditText)findViewById(R.id.email);
        phone=(EditText)findViewById(R.id.phone);
        password=(EditText)findViewById(R.id.pass);
        Button reg= (Button) findViewById(R.id.register);
        reg.setOnClickListener(this);

    }

    private void registerUser(){

      final  String email=e.getText().toString().trim();
      final  String pass=password.getText().toString().trim();
      final  String uname=name.getText().toString();
      final  String phoneNo=phone.getText().toString();

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
                    user=FirebaseAuth.getInstance().getCurrentUser();
                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(register.this,"Email send for verification",Toast.LENGTH_SHORT);
                           }else{
                               Toast.makeText(register.this,"Email send for verification Error",Toast.LENGTH_SHORT);
                           }
                        }
                    });

                    // String id=ref.push().getKey();
                    String id=user.getUid();
                    UserData user=new UserData(uname,email,phoneNo,pass);
                    ref.child(id).setValue(user);

                    Toast.makeText(register.this,"Succesfully Registerd",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),SignInUser.class));
                }else{
                    Toast.makeText(register.this,"Registeration Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }//end of registerUser() method

    public void onClick(View view) {
          registerUser();

    }
}
