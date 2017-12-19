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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;


public class SignInUser extends AppCompatActivity  {

    private EditText email,password;
    private Button submit,forgot;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_sign_in_user);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        forgot = findViewById(R.id.reset);
        progressBar=findViewById(R.id.progressBar2);

        //facebook login
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
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



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e=email.getText().toString();
                String p=password.getText().toString();
                firebaseAuth = FirebaseAuth.getInstance();

                if(TextUtils.isEmpty(e)){
                    Toast.makeText(SignInUser.this,"Please Enter Email ID",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(p)){
                    Toast.makeText(SignInUser.this,"Plase Enter Password",Toast.LENGTH_LONG).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(SignInUser.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //   FirebaseUser user=firebaseAuth.getCurrentUser();
                            progressBar.setVisibility(View.GONE);
                            startActivity(new Intent(SignInUser.this,Home_page.class));
                        }else{
                            Toast.makeText(SignInUser.this,"Invalid Email or password",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInUser.this, Reset_password.class));
            }
        });

    }//end of onCreate() method

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void signInWithFacebook(AccessToken token) {

        firebaseAuth=FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Log.e("credital",credential.toString());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        boolean log=task.isSuccessful();
                        Log.e("login",""+log);
                        if (task.isSuccessful()) {

                           // startActivity(new Intent(SignInUser.this,Home_page.class));
                            Toast.makeText(SignInUser.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                           startActivity(new Intent(SignInUser.this,Home_page.class));
                        }
                    }
                });
    }

}//end of class
