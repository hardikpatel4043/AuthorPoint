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

import com.example.hardik.myapplication.POJO.Author;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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

    private SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 1;


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
        signInButton=findViewById(R.id.login_with_google);

        register_click=findViewById(R.id.author_register_button);
        register_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        //Google signIN method
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }//End of onCreate() method


    //--------------------------------------Google login-----------------------------------
    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {

                            Toast.makeText(AuthorRegisteration.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            //startActivity(new Intent(SignInUser.this,Home_page.class));
                            user=FirebaseAuth.getInstance().getCurrentUser();
                            String id=user.getUid();
                            Author dataEnter=new Author("NA","default","defalut",
                                    user.getEmail(),user.getDisplayName(),"default","default",""+ ServerValue.TIMESTAMP,"author");
                            mDatabase.child(id).setValue(dataEnter);

                            Intent i = new Intent(AuthorRegisteration.this, MainActivity.class);
                            // set the new task and clear flags
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            //   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                    }
                });
    }
    //------------------------------------------------------------------------

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
                    Author dataEnter=new Author(phone,"default","defalut",
                            emailInput,uname,"default","default",""+ ServerValue.TIMESTAMP,"author");
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
