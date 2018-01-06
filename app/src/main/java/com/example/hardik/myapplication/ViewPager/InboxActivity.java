package com.example.hardik.myapplication.ViewPager;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.hardik.myapplication.HomePageFragment;
import com.example.hardik.myapplication.R;
import com.example.hardik.myapplication.recycle_home.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class InboxActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private InboxActivityAdapter inboxActivityAdapter;

    private DatabaseReference mRootRef;
    private FirebaseUser mCurrentuser;


    @Override
    protected void onStart() {
        super.onStart();

            mRootRef.child("author").child(mCurrentuser.getUid()).child("online").setValue("true");

    }//End of onStart() method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);

        //Firebase
        mRootRef= FirebaseDatabase.getInstance().getReference();
        mCurrentuser=FirebaseAuth.getInstance().getCurrentUser();

        toolbar=findViewById(R.id.inbox_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AuthorPoint Chat DashBoard");

        viewPager=findViewById(R.id.inbox_view_pager);
        inboxActivityAdapter =new InboxActivityAdapter(getSupportFragmentManager());
        viewPager.setAdapter(inboxActivityAdapter);

        viewPager.setCurrentItem(1);

        tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
