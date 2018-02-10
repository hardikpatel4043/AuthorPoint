package com.example.hardik.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPVerification extends AppCompatActivity {

    EditText mobile,otp;
    Button   verify,submit;
    FirebaseAuth cAuth;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpverification);

        mobile=findViewById(R.id.editText);
        otp=findViewById(R.id.editText2);
        verify=findViewById(R.id.verify);
        submit=findViewById(R.id.submit);

        cAuth=FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        mobile.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        OTPVerification.this,
                        mCallbacks);

                mobile.setVisibility(View.GONE);
                submit.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
                verify.setVisibility(View.VISIBLE);
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Toast.makeText(OTPVerification.this,"Verified",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OTPVerification.this,"Login Faled",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(OTPVerification.this,"Otp Send to your mobile no",Toast.LENGTH_LONG).show();
                mVerificationId = verificationId;
                mResendToken = token;

            }
        };


    }//onCreate() close
}
