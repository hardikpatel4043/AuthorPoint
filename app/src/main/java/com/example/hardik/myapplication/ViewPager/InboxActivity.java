package com.example.hardik.myapplication.ViewPager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.hardik.myapplication.R;


public class InboxActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private InboxActivityAdapter inboxActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbox_activity);

        toolbar=findViewById(R.id.inbox_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AuthorPoint Chat DashBoard");

        viewPager=findViewById(R.id.inbox_view_pager);
        inboxActivityAdapter =new InboxActivityAdapter(getSupportFragmentManager());
        viewPager.setAdapter(inboxActivityAdapter);

        tabLayout=findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);



    }
}
