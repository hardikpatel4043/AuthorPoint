package com.example.hardik.myapplication;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class AuthorList extends android.support.v4.app.Fragment {


    private List<AuthorRegister> authorList = new ArrayList<>();
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

        return rootView;
    }

    void prepareAuthorData(){
        mref=FirebaseDatabase.getInstance().getReference("author");

        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
//                    HashMap<String,String> value= (HashMap<String, String>) snapshot.getValue();
//                    String name=value.get("name");
//                    String imageUrl=value.get("imageUrl");
//                    Log.e("name",""+name);
//                    Log.e("url",""+imageUrl);
//                   AuthorData authorData=new AuthorData(name,imageUrl);

                     AuthorRegister authorData = snapshot.getValue(AuthorRegister.class);
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
