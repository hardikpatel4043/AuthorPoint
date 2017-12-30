package com.example.hardik.myapplication.ViewPager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hardik.myapplication.FriendRequestAdapter;
import com.example.hardik.myapplication.POJO.AuthorRegister;
import com.example.hardik.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendRequestTab extends Fragment {

    private DatabaseReference mFriendReqDatabase;
    private DatabaseReference mAuthorDetail;
    private FirebaseUser currentUser;

    public List<String> friendReqList;
    private List<AuthorRegister> mAuthorFriendList;

    private RecyclerView recyclerView;
    private FriendRequestAdapter mAdapter;


    public FriendRequestTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.friend_request_recyclerview, container, false);
        friendReqList=new ArrayList<>();
        mAuthorFriendList=new ArrayList<>();

        recyclerView = rootView.findViewById(R.id.recycler_friend_req);

        mAdapter = new FriendRequestAdapter(getActivity(),mAuthorFriendList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(mLayoutManager);

        mAuthorDetail=FirebaseDatabase.getInstance().getReference("author");
        mFriendReqDatabase= FirebaseDatabase.getInstance().getReference().child("friends_req");

        currentUser= FirebaseAuth.getInstance().getCurrentUser();

        String currentUserId=currentUser.getUid().toString();

        mFriendReqDatabase.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    String req=snapshot.getKey();
                    String f=dataSnapshot.child(req).child("request_type").getValue().toString();

                    if(f.equals("received")){
                        friendReqList.add(req);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });//End of mFriendReqDatabase

        mAuthorDetail.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(int i=0;i<friendReqList.size();i++){
                    String getId=friendReqList.get(i);

                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String id=snapshot.getKey().toString();

                        if(id.equals(getId)){
                            AuthorRegister authorData = snapshot.getValue(AuthorRegister.class);
                            mAuthorFriendList.add(authorData);
                        }
                    }
                }
                recyclerView.setAdapter(mAdapter);
            }//End of onDataChange method

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });//End of mAuthorDetail






        return rootView;
    }//End of onCreateView() method

}//End of Class
