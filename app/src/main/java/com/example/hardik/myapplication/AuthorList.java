package com.example.hardik.myapplication;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardik.myapplication.recycle_home.Author;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * this class is main class for authorList in vertical view
 */
public class AuthorList extends Fragment {

    private List<Bitmap> authorList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AuthorListAdapter mAdapter;
    private DatabaseReference mref;
    String encodeImage;
    Bitmap bm;
    ValueEventListener valueEventListener;

    public AuthorList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView=inflater.inflate(R.layout.author_list_vertical, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_author_list);
        mAdapter = new AuthorListAdapter(authorList);
      //  recyclerView.setHasFixedSize(true);
        mref=FirebaseDatabase.getInstance().getReference().child("AuthorImages");

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(mAdapter);
        // row click listener
        valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                authorList.clear();
                for(DataSnapshot snapshot1: dataSnapshot.getChildren()){
                    encodeImage=snapshot1.getValue(String.class);
                    bm=decodeFromBase64ToBitmap(encodeImage);
                    authorList.add(bm);

                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        return rootView;

    }

    private Bitmap decodeFromBase64ToBitmap(String encodedImage)

    {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }

    @Override
    public void onResume() {
        super.onResume();
        mref.addValueEventListener(valueEventListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mref.removeEventListener(valueEventListener);
    }




}
