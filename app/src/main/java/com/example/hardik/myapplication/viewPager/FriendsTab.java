package com.example.hardik.myapplication.viewPager;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hardik.myapplication.pojo.Author;
import com.example.hardik.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FriendsTab extends Fragment {

    private List<Author> mFriendsDetailList;
    private List<String> mFriendIdList;
    private  String currentUserId;

    RecyclerView recyclerView;
    DatabaseReference mFriendDatabase;
    DatabaseReference mFriendDetailDatabase;
    FirebaseUser mCurrentUser;

    FriendsTabAdapter mAdapter;

    public FriendsTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.friends_tab_recyclerview,container,false);

        mFriendsDetailList=new ArrayList<>();
        mFriendIdList=new ArrayList<>();

        recyclerView=rootView.findViewById(R.id.recycler_friends_tab);

        mFriendDatabase= FirebaseDatabase.getInstance().getReference("friends");
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        mFriendDetailDatabase=FirebaseDatabase.getInstance().getReference().child("author");

        mAdapter = new FriendsTabAdapter(getActivity(),mFriendsDetailList,mFriendIdList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        currentUserId=mCurrentUser.getUid().toString();

        mFriendDatabase.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mFriendIdList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String friendId=snapshot.getKey();
                    String valueId=snapshot.getValue().toString();

                    if(!valueId.isEmpty()){
                        mFriendIdList.add(friendId);
                    }
                }

                mFriendDetailDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        mFriendsDetailList.clear();
                        for(int i=0;i<mFriendIdList.size();i++){
                            String getId=mFriendIdList.get(i);

                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                String id=snapshot.getKey().toString();

                                if(id.equals(getId)){
                                    Author authorData = snapshot.getValue(Author.class);
                                    mFriendsDetailList.add(authorData);
                                }
                            }
                        }
                        recyclerView.setAdapter(mAdapter);

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return rootView;
    }

}
