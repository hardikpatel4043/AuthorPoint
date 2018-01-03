package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hardik.myapplication.POJO.AuthorRegister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class AuthorRegisteration extends AppCompatActivity {

    Button register_click;
    TextInputLayout name,password,email,phoneNo;

    //firebase
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.author_registeration);

        //firebase
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference("author");

        name=findViewById(R.id.author_register_name);
        password=findViewById(R.id.author_register_pasword);
        email=findViewById(R.id.author_register_email);
        phoneNo=findViewById(R.id.author_register_mobile);

        register_click=findViewById(R.id.author_register_button);
        register_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        final  String emailInput=email.getEditText().getText().toString().trim();
        final  String pass=password.getEditText().getText().toString().trim();
        final  String uname=name.getEditText().getText().toString();
        final  String phone=phoneNo.getEditText().getText().toString();

        if (TextUtils.isEmpty(emailInput) || TextUtils.isEmpty(pass) || TextUtils.isEmpty(uname) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this,"Please Enter Required Field",Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(emailInput,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    user=FirebaseAuth.getInstance().getCurrentUser();

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AuthorRegisteration.this,"Email send for verification",Toast.LENGTH_SHORT);
                            }else{
                                Toast.makeText(AuthorRegisteration.this,"Email send for verification Error",Toast.LENGTH_SHORT);
                            }
                        }
                    });

                    // String id=ref.push().getKey();
                    String id=user.getUid();
                    AuthorRegister dataEnter=new AuthorRegister(phone,"default","defalut",
                            emailInput,uname,"default","default",""+ ServerValue.TIMESTAMP);
                    mDatabase.child(id).setValue(dataEnter);

                    Toast.makeText(AuthorRegisteration.this,"Succesfully Registerd",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),SignInUser.class));
                    finish();
                }else{
                    Toast.makeText(AuthorRegisteration.this,"Registeration Error",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
