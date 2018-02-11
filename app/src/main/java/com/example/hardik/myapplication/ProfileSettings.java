package com.example.hardik.myapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ProfileSettings extends AppCompatActivity {

    Button changePassword;
    ImageView profile,profileImage;
    StorageReference mImageRef;
    DatabaseReference mRootRef;
    FirebaseUser mCurrentUser=FirebaseAuth.getInstance().getCurrentUser();;
    String current_user_id=mCurrentUser.getUid();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_settings);
        //SharedPreferences
        SharedPreferences pref=getApplicationContext().getSharedPreferences("UserDetail",0);

        mImageRef= FirebaseStorage.getInstance().getReference();

        mRootRef= FirebaseDatabase.getInstance().getReference();

       String accountType= pref.getString("user_login_type",null);
        profile=findViewById(R.id.profile_image);
        profileImage=findViewById(R.id.profile_setting_change_profile);
        changePassword=findViewById(R.id.profile_change_password_button);

        if(accountType.equals("google")){
            changePassword.setEnabled(false);
        }

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfileSettings.this,ChangePassword.class));

            }
        });

        mRootRef.child("author").child(current_user_id).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String imageUrl=dataSnapshot.getValue().toString();
                if(imageUrl.equals("defualt")){
                    Glide.with(getApplicationContext()).load(R.drawable.default_avatar).apply(RequestOptions.circleCropTransform()).into(profile);
                }
                Glide.with(getApplicationContext()).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(ProfileSettings.this);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                Glide.with(getApplicationContext()).load(resultUri).apply(RequestOptions.circleCropTransform()).into(profile);

                mImageRef.child("AuthorImages").child(current_user_id+".jpg").putFile(resultUri)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                String download_url=task.getResult().getDownloadUrl().toString();

                                mRootRef.child("author").child(current_user_id).child("image").setValue(download_url);

                            }
                        });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
            }
        }
    }
}
