package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;


public class SignInUser extends AppCompatActivity  {

    private EditText email,password;
    private Button submit,forgot;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_user);

        mAuth=FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        forgot = findViewById(R.id.reset);
        progressBar=findViewById(R.id.progressBar2);


        //facebook login
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setLoginBehavior(LoginBehavior.NATIVE_WITH_FALLBACK);
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                signInWithFacebook(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"Error on Lgoin",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Error on Lgoin",Toast.LENGTH_SHORT).show();
            }
        });

        //------------------------login with email and password-----------------------------
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e=email.getText().toString();
                String p=password.getText().toString();

                if(TextUtils.isEmpty(e)){
                    Toast.makeText(SignInUser.this,"Please Enter Email ID",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(p)){
                    Toast.makeText(SignInUser.this,"Plase Enter Password",Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(SignInUser.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
                            String current_user_id=user.getUid();

                            DatabaseReference mUser= FirebaseDatabase.getInstance().getReference("author");

                            String token_id= FirebaseInstanceId.getInstance().getToken();
                            mUser.child(current_user_id).child("device_token").setValue(token_id);
                            progressBar.setVisibility(View.GONE);

                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Intent i=new Intent(SignInUser.this,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);

                        }else{
                            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            Toast.makeText(SignInUser.this,"Invalid Email or password",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        //--------------------forgot password option---------------------
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInUser.this, ResetPassword.class));

            }
        });

    }//end of onCreate() method

    //-------------------------login with facebook-------------------
    private void signInWithFacebook(AccessToken token) {
        // progressBar.setVisibility(View.VISIBLE);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Log.e("credital",credential.toString());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                            Toast.makeText(SignInUser.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Intent i=new Intent(SignInUser.this,MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                    }
                });
    }
}//end of class
