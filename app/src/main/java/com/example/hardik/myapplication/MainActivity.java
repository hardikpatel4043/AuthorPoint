package com.example.hardik.myapplication;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hardik.myapplication.ViewPager.InboxActivity;
import com.example.hardik.myapplication.recycle_home.StartActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    DatabaseReference mUserRef;


    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth=FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),StartActivity.class));
            finish();
        }else {

            mUserRef.child("online").setValue("true");
        }

    }//End of onStart() method

    @Override
    protected void onDestroy() {
        super.onDestroy();

        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null){
            mUserRef.child("online").setValue("offline");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();

        if(firebaseAuth.getCurrentUser()!=null){

        mUserRef = FirebaseDatabase.getInstance().getReference("author").child(user.getUid());

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        TextView text=navigationView.getHeaderView(0).findViewById(R.id.main_page_email);
        final TextView userName=navigationView.getHeaderView(0).findViewById(R.id.main_page_name);
        final ImageView profileImage=navigationView.getHeaderView(0).findViewById(R.id.main_page_profile);

        if(firebaseAuth.getCurrentUser()!=null){
            Uri photoUrl=user.getPhotoUrl();
            text.setText(user.getEmail());

            if(photoUrl==null){
                mUserRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Glide.with(getApplicationContext()).load(dataSnapshot.child("image").getValue()).apply(RequestOptions.circleCropTransform()).into(profileImage);
                        userName.setText(dataSnapshot.child("name").getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            userName.setText(user.getDisplayName());
            Glide.with(getApplicationContext()).load(photoUrl).apply(RequestOptions.circleCropTransform()).into(profileImage);

        }
        navigationView.setNavigationItemSelectedListener(this);

        //set homepage on loading
        FragmentTransaction tx=getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame,new HomePageFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(MainActivity.this,ProfileSettings.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home_page) {
            // Handle the camera action
            setTitle("Homepage");
            HomePageFragment fragment=new HomePageFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_booklist) {

            setTitle("Books");
            BookList fragment=new BookList();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_author_list) {

            setTitle("Authors");
            AuthorList fragment=new AuthorList();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();

        }else if(id==R.id.inbox){

            //startActivity(new Intent(MainActivity.this,EnterDataTemp.class));
                startActivity(new Intent(MainActivity.this, InboxActivity.class));

        } else if (id == R.id.nav_logout) {

            LoginManager.getInstance().logOut();
            FirebaseAuth.getInstance().signOut();
            //To stop user from login without password after logout

            mUserRef.child("online").setValue("offline");

            startActivity(new Intent(getApplicationContext(),StartActivity.class));
            finish();

        }else if(id==R.id.nav_event){

            startActivity(new Intent(getApplicationContext(),EventUpload.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
