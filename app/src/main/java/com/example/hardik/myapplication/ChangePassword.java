package com.example.hardik.myapplication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 *
 */
public class ChangePassword extends Fragment {

    EditText password,currentPassword;
    Button submit;
    FirebaseUser user;
    AuthCredential  credential;
    String pass;

    public ChangePassword() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.change_password, container, false);
        password=  rootView.findViewById(R.id.new_password);
        currentPassword=rootView.findViewById(R.id.current_password);
        submit=(Button) rootView.findViewById(R.id.submit);
        user= FirebaseAuth.getInstance().getCurrentUser();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pass=password.getText().toString().trim();
                String currentPass=currentPassword.getText().toString().trim();
                if(TextUtils.isEmpty(currentPass)){
                    Toast.makeText(getActivity(),"Please Enter current password",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getActivity(),"Please Enter New password",Toast.LENGTH_SHORT).show();
                    return;
                }

                credential= EmailAuthProvider.getCredential(user.getEmail(),currentPass);

                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                user.updatePassword(pass)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getActivity(), "password chagne succesfull", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getActivity(), "Password Change Error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }else{
                                Toast.makeText(getActivity(), "Reauthentication failed", Toast.LENGTH_SHORT).show();
                            }
                            }
                        });
            }
        });


        return rootView;
    }

}
