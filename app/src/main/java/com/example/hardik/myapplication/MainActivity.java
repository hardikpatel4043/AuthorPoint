package com.example.hardik.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hardik.myapplication.pojo.CheckNetwork;
import com.example.hardik.myapplication.viewPager.InboxActivity;
import com.example.hardik.myapplication.recycle_home.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    DatabaseReference mUserRef;
    DrawerLayout drawerLayout;
    boolean doubleBackToExitPressedOnce = false;
    NavigationView navigationView;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth=FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(getApplicationContext(),StartActivity.class));
            finish();
        }else if(!firebaseAuth.getCurrentUser().isEmailVerified()){
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
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);


        //-------SharedPreferences-------
        SharedPreferences pref=getApplicationContext().getSharedPreferences("UserDetail",0);
        final SharedPreferences.Editor editor=pref.edit();

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

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

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
                        editor.putString("user_login_type","normal_account");
                        editor.putString("user_type",dataSnapshot.child("type").getValue().toString());
                        editor.commit();
                        Glide.with(getApplicationContext()).load(R.drawable.default_avatar).apply(RequestOptions.circleCropTransform()).into(profileImage);
                        userName.setText(dataSnapshot.child("name").getValue().toString());

                        //------------------------------------------------------------------------
                        String user=dataSnapshot.child("type").getValue().toString();
                        if(user.equals("reader")){
                            Menu nav_Menu = navigationView.getMenu();
                            nav_Menu.findItem(R.id.nav_event_upload).setVisible(false);
                            nav_Menu.findItem(R.id.nav_book_upload).setVisible(false);
                        }
                        //--------------------------------------------------------------------------
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }else{
                editor.putString("user_login_type","google");
                editor.commit();
                userName.setText(user.getDisplayName());
                Glide.with(getApplicationContext()).load(photoUrl).apply(RequestOptions.circleCropTransform()).into(profileImage);
            }

        }
        navigationView.setNavigationItemSelectedListener(this);

        //set homepage on loading
        FragmentTransaction tx=getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.frame,new HomePageFragment()).commit();

        //Check Internet Connection
        if(!CheckNetwork.isInternetAvailable(getApplicationContext())){
            Toast.makeText(MainActivity.this, "Network is not Available ",Toast.LENGTH_LONG).show();
        }

    }//End of onCreate() method

    //----------------------------------------------------------------------------------------------


    private int checkNavigationMenuItem() {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).isChecked())
                return i;
        }
        return -1;
    }

    @Override
    public void onBackPressed() {

        if (checkNavigationMenuItem() != 0) {
            navigationView.setCheckedItem(R.id.nav_home_page);
//            FragmentTransaction tx=getSupportFragmentManager().beginTransaction();
//            tx.replace(R.id.frame,new HomePageFragment()).commit();
            Fragment fragment=new HomePageFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();

        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Snackbar snackbar = Snackbar.make(drawerLayout, "Press again to exit", Snackbar.LENGTH_LONG);
            View snackbarView=snackbar.getView();
            snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
            snackbar.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);


        }

//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Snackbar snackbar = Snackbar.make(drawerLayout, "Press again to exit", Snackbar.LENGTH_LONG);
//        View snackbarView=snackbar.getView();
//        snackbarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.colorPrimaryDark));
//        snackbar.show();
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce = false;
//            }
//        }, 2000);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }//End of onBackPressed() method

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

            HomePageFragment fragment=new HomePageFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_booklist) {


            BookList fragment=new BookList();
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_author_list) {


            AuthorList fragment=new AuthorList();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();

        }else if(id==R.id.inbox){

            //startActivity(new Intent(MainActivity.this,EnterDataTemp.class));
                startActivity(new Intent(MainActivity.this, InboxActivity.class));

        } else if (id == R.id.nav_logout) {

            FirebaseAuth.getInstance().signOut();
            //To stop user from login without password after logout

            mUserRef.child("online").setValue("offline");

            startActivity(new Intent(getApplicationContext(),StartActivity.class));
            finish();

//        }else if(id==R.id.nav_event){
//
//          //  startActivity(new Intent(getApplicationContext(),EventUpload.class));
//
        }else if(id==R.id.nav_event_upload){

            EventUpload fragment=new EventUpload();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();

        }else if(id==R.id.nav_book_upload){

            BookUpload fragment=new BookUpload();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,fragment);
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
