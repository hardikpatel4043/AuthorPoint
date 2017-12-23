package com.example.hardik.myapplication;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.example.hardik.myapplication.recycle_home.Author;
import com.example.hardik.myapplication.recycle_home.AuthorData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * this class is main class for authorList in vertical view
 */
public class AuthorList extends android.support.v4.app.Fragment {


    private List<AuthorData> authorList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AuthorListAdapter mAdapter;
    private DatabaseReference mref;


    public AuthorList() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.author_list_vertical, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_author_list);
        mAdapter = new AuthorListAdapter(getActivity(),authorList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        prepareAuthorData();


        // row click listener

        return rootView;
    }

    void prepareAuthorData(){
        mref=FirebaseDatabase.getInstance().getReference("author");

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(getActivity()==null){
                    return;
                }
                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    HashMap<String,String> value= (HashMap<String, String>) snapshot.getValue();
                    String name=value.get("name");
                    String imageUrl=value.get("imageUrl");
                    Log.e("name",""+name);
                    Log.e("url",""+imageUrl);
                   AuthorData authorData=new AuthorData(name,imageUrl);

                    //   AuthorData authorData = snapshot.getValue(AuthorData.class);
                    authorList.add(authorData);
                }
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mAdapter.notifyDataSetChanged();
    }
}
